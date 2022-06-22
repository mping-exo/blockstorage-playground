(ns bsstore.core
  (:require [exoscale.entity.blockstorage :as bs]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.entity.blockstorage.rcblob :as rcblob]
            [exoscale.blockstorage.bsstore :as bsstore]
            [exoscale.vinyl.store :as store]
            [exoscale.vinyl.query :as query]
            [exoscale.blockstorage.bsstore.payload :as p]
            [com.stuartsierra.component :as component]
            [clojure.spec.alpha :as s])
  (:import [java.util UUID]))

(def store (->
             {:env          "blockstorage"
              :cluster-file "/etc/foundationdb/fdb.cluster"}
             (bsstore/make-bsstore)
             (component/start)))

(def uuid (str (UUID/randomUUID)))


(query/build-query
  :Extent [:and
           [:= :uuid (str uuid)]
           [:= :diskOffset 10]])

(comment
  (bs/-run-in-transaction store
    (fn [s]
      (rcblob/-get-by-id s 1 1)))

  (bs/-run-in-transaction store
    (fn [s]
      (rcblob/-insert s
        #:exoscale.entity.blockstorage.rcblob{:blob     #:exoscale.entity.sos.blob{:id 1, :partition 1, :size 1829548},
                                              :refcount 5267984})))

  @(bs/-run-in-transaction store
     (fn [s]
       (store/list-query (:store s)
         (query/build-query
           :Extent [:and
                    [:= :uuid (str uuid)]
                    [:= :diskOffset 10]])
         {::store/transform p/parse-record})))

  @(bs/-run-in-transaction store
     (fn [s] (extent/-get-by-offset s uuid 10)))

  @(store/long-range-reduce (:db store) conj [] :Extent [(str uuid) nil])

  @(extent/-long-range-reduce store conj [] uuid)

  ;(bs/-reduce-extents store uuid conj [] {:max-keys 100})

  @(bs/-run-in-transaction store
     (fn [s] (extent/-get-all s)))

  (bs/-run-in-transaction store
    (fn [s]
      (extent/-insert s (extent/make-extent uuid (rand-int 1000) []))))

  "")
