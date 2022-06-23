(ns exoscale.blockstorage.loadtest
  (:gen-class)
  (:require [exoscale.entity.blockstorage :as bs]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.entity.blockstorage.rcblob :as rcblob]
            [exoscale.entity.blockstorage.blobview :as bv]
            [exoscale.entity.sos.blob :as blob]
            [exoscale.blockstorage.bsstore :as bsstore]
            [com.stuartsierra.component :as component]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g]
            [exoscale.vinyl.store :as store])
  (:use [criterium.core])
  (:import [java.util UUID]))

(defonce pid (atom 0))
(defonce bid (atom 0))

(defn- wrap-int [v]
  (if (< v Integer/MAX_VALUE)
    (inc v)
    0))

(defn partition-id [] @pid)
(defn blob-id [] (swap! bid (fn [v] (if (< v Integer/MAX_VALUE)
                                      (inc v)
                                      (do
                                        (swap! pid wrap-int)
                                        0)))))

(defn update-blob-ids [bv]
  (let [bid (blob-id)
        pid (partition-id)]
    (-> bv
      (assoc-in [::bv/partial-blob ::blob/blob ::blob/id] bid)
      (assoc-in [::bv/partial-blob ::blob/blob ::blob/partition] pid))))

(defn- insert-extent-tx
  "Insert an extent"
  [store ext]
  (bs/-run-in-transaction store
    (fn [s]
      (doseq [blob (extent/blobs ext)]
        (let [rcblob (rcblob/make-rcblob blob 1)]
          (rcblob/-insert s rcblob)))
      (extent/-insert s ext))))

(def ^:private extent-generator (s/gen ::extent/extent))
(def fixed-uuid (UUID/randomUUID))

(defn- rand-extent
  "Create a random extent"
  [uuid disk-offset]
  (let [ext       (-> (g/generate extent-generator)
                    (assoc ::extent/uuid uuid)
                    (assoc ::extent/disk-offset disk-offset))]
    (update-in ext [::extent/blob-views]
      (fn [bvs]
        (map update-blob-ids bvs)))))

;;;;
;; main
(defn- run-load-test [& args]
  (let [store (-> {:env          "blockstorage"
                   :cluster-file "/etc/foundationdb/fdb.cluster"}
                (bsstore/make-bsstore)
                (component/start))
        counter (atom 0)
        threads 20
        per-thread 5000]

    (->> (range threads)
         (pmap (fn [tgroup]
                 (let [startoff (* tgroup per-thread)]
                   (dotimes [offset per-thread]
                     (let [ext (rand-extent fixed-uuid (+ startoff offset))]
                       (swap! counter inc)
                       (insert-extent-tx store ext)
                       (when (= 0 (rem @counter 500))
                         (println (format "Inserted %s/%s extents with uuid %s" @counter (* per-thread threads) fixed-uuid))))))
                 ;; returnval
                 per-thread))
         (reduce +)
         ((fn [tot] (println "Finalized insertion of " tot "extents"))))))

(defn -main [& args]
  (run-load-test args))

(comment
  ;;  Execution time upper quantile : 789,801243 µs (97,5%)
  (with-progress-reporting (quick-bench (rand-extent fixed-uuid 0) :verbose))

  (def store (-> {:env          "blockstorage"
                  :cluster-file "/etc/foundationdb/fdb.cluster"}
               (bsstore/make-bsstore)
               (component/start)))

  ;; Execution time upper quantile : 5,303995 ms (97,5%)
  (with-progress-reporting (quick-bench (insert-extent-tx store (rand-extent fixed-uuid (rand-int Integer/MAX_VALUE))) :verbose))

  (count
    @(store/long-range-reduce (:db store) conj [] :Extent [(str fixed-uuid) nil]))

  (time
    (run-load-test))

  "")