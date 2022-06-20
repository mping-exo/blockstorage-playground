(ns bsstore.core
  (:require [exoscale.blockstorage.bsstore :as bs]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.vinyl.store :as store]
            [exoscale.vinyl.query :as query]
            [exoscale.blockstorage.bsstore.payload :as p]
            [com.stuartsierra.component :as component])
  (:import [java.util UUID]))

(def store (->
             {:env          "bsstore"
              :cluster-file "/etc/foundationdb/fdb.cluster"}
             (bs/make-bsstore)
             (component/start)))

(comment

  @(bs/-run-in-transaction store
     (fn [s]
       (store/list-query (:store s)
         (query/build-query
           :Extent [:and
                    [:= :uuid "09e7bed7-94d7-4d31-8771-4149f331312d"]
                    [:= :diskOffset 10]])
         {::store/transform p/parse-record})))


  @(bs/-run-in-transaction store
     (fn [s] (extent/-get-by-offset s "09e7bed7-94d7-4d31-8771-4149f331312d" 10)))

  @(bs/-run-in-transaction store
     (fn [s] (extent/-get-all s)))

  (bs/-run-in-transaction store
    (fn [s] (extent/-insert s (extent/make-extent (str (UUID/randomUUID)) 10 []))))

  "")
