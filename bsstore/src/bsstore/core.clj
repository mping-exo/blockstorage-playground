(ns bsstore.core
  (:require [exoscale.entity.blockstorage :as bs]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.entity.blockstorage.rcblob :as rcblob]
            [exoscale.blockstorage.bsstore :as bsstore]
            [exoscale.vinyl.store :as store]
            [exoscale.vinyl.query :as query]
            [com.stuartsierra.component :as component])
  (:import [java.util UUID]))

(def store (->
             {:env          "blockstorage"
              :cluster-file "/etc/foundationdb/fdb.cluster"}
             (bsstore/make-bsstore)
             (component/start)))

(def uuid (str (UUID/randomUUID)))

(comment
  (bs/-run-in-transaction store
    (fn [s]
      (rcblob/-get-by-id s 1 1)))

  (bs/-run-in-transaction store
    (fn [s]
      (rcblob/-insert s
        #:exoscale.entity.blockstorage.rcblob{:blob     #:exoscale.entity.sos.blob{:id 1, :partition 1, :size 1829548},
                                              :refcount 5267984})))

  @(store/long-query-reduce (:db store) conj [] (query/build-query
                                                  :Extent [:and
                                                           [:= :uuid (str uuid)]
                                                           [:>= :diskOffset 4096]
                                                           [:<= :diskOffset 111111]]))

  @(bs/-run-in-transaction store
     (fn [s] (extent/-get-by-offset s uuid 10)))

  @(bs/-run-in-transaction store
     (fn [s] (extent/-get-all s)))

  (bs/-run-in-transaction store
    (fn [s]
      (extent/-insert s (extent/make-extent uuid 0 []))
      (extent/-insert s (extent/make-extent uuid 4096 []))
      (extent/-insert s (extent/make-extent uuid 8192 []))
      (extent/-insert s (extent/make-extent uuid 12288 []))
      (extent/-insert s (extent/make-extent uuid 16384 []))
      (extent/-insert s (extent/make-extent uuid 40960 []))
      nil))

  "")
