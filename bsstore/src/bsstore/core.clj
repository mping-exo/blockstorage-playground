(ns bsstore.core
  (:require [exoscale.entity.blockstorage :as bs]
            [exoscale.entity.blockstorage.extent :as extent]
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

(def uuid (UUID/randomUUID))


(query/build-query
  :Extent [:and
           [:= :uuid (str uuid)]
           [:= :diskOffset 10]])

(comment

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

  @(bs/-run-in-transaction store
     (fn [s] (extent/-get-all s)))

  (bs/-run-in-transaction store
    (fn [s]
      (extent/-insert s (extent/make-extent uuid 10 []))))

  "")
