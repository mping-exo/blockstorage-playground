(ns user
  (:require [com.stuartsierra.component :as component]
            [exoscale.blockstorage.nbdserver :as nbdserver]
            [exoscale.blockstorage.nbdfileimpl :as fileimpl]
            [exoscale.blockstorage.bsstore :as bsstore]
            [exoscale.blockstorage.protocols :as p]
            [exoscale.entity.blockstorage :as bs]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.entity.blockstorage.rcblob :as rcblob]
            [exoscale.entity.blockstorage.blobview :as bv]
            [exoscale.entity.sos.blob :as blob]))

(defmacro safely [& body]
  `(try
     (do ~@body)
     (catch Exception e#
       (.printStackTrace e#))))

(defonce pid (atom 0))
(defonce bid (atom 0))

(defn- wrap-int [v]
  (if (< v Integer/MAX_VALUE)
    (inc v)
    0))

(defn- partition-id [] @pid)
(defn- blob-id [] (swap! bid (fn [v] (if (< v Integer/MAX_VALUE)
                                       (inc v)
                                       (do
                                         (swap! pid wrap-int)
                                         0)))))

(defn handle->uuid [_] "ad6be94d-6c4c-4553-a932-0ead96443039")
(def maxblob (* 16 1024 1024))

(defn- insert-extent-tx
  "Insert an extent"
  [store ext]
  (bs/-run-in-transaction store
    (fn [s]
      (doseq [blob (extent/blobs ext)]
        (let [rcblob (rcblob/make-rcblob blob 1)]
          (rcblob/-insert s rcblob)))
      (extent/-insert s ext))))

(defn- -read [bsstore handle len offset]
  (bs/-run-in-transaction bsstore
    (fn [s]
      (let [extents (extent/reduce-extents s conj [] (handle->uuid handle) offset len)]
        (println "Got extents: " extents))))
  (println "FDBxx READ"))

(defn- make-rand-blobviews [offset len]
  [])

(defn- -write [bsstore handle len offset]
  (let [rand-bvs (make-rand-blobviews offset len)
        ext      (extent/make-extent (handle->uuid handle) offset rand-bvs)]
    (bs/-run-in-transaction bsstore
      (fn [s] (extent/-insert s ext))))
  (println "FDBxx WRITE"))

(defn- -trim [bsstore handle len offset]
  (println "FDBxx TRIM"))

(defrecord FdbNbdStore [bsstore size]
  p/NBDImpl
  (nbd-max-size [this]
    size)
  (nbd-read [this handle din dout len offset]
    (safely (-read bsstore handle len offset)))
  (nbd-write [this handle din dout len offset]
    (safely (-write bsstore handle len offset)))
  (nbd-trim [this handle din dout len offset]
    (safely (-trim bsstore handle len offset)))
  (nbd-flush [this handle din dout])
  (nbd-cache [this handle din dout]))

(def maxsize (* 1024 1024 1024))
(def system (component/system-map
              :bsstore (bsstore/make-bsstore {:env          "blockstorage"
                                              :cluster-file "/etc/foundationdb/fdb.cluster"})
              :fileimpl (fileimpl/make-file-impl maxsize)
              :size maxsize
              :fdbstore (component/using
                          (map->FdbNbdStore {})
                          {:bsstore :bsstore
                           :size    :size})
              :server (component/using
                        (nbdserver/map->NBDServer {})
                        {:size      :size
                         :impl      :fileimpl
                         :metastore :fdbstore})))

(component/stop system)
(component/start system)