(ns exoscale.blockstorage.loadtest
  (:gen-class)
  (:require [exoscale.entity.blockstorage :as bs]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.blockstorage.bsstore :as bsstore]
            [exoscale.vinyl.store :as store]
            [exoscale.vinyl.query :as query]
            [exoscale.blockstorage.bsstore.payload :as p]
            [com.stuartsierra.component :as component]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g])
  (:import [java.util UUID]))

(defn- insert-extent-tx [store ext]
  (bs/-run-in-transaction store
    (fn [s]
      (extent/-insert s ext))))

(defn- rand-extent []
  (g/generate (s/gen ::extent/extent)))

(defn- run-load-test [& args]
  (let [store (-> {:env          "blockstorage"
                   :cluster-file "/etc/foundationdb/fdb.cluster"}
                (bsstore/make-bsstore)
                (component/start))]

    (bs/-run-in-transaction store
      (fn [s] (insert-extent-tx store (rand-extent))))))

(defn -main [& args]
  (run-load-test args))


(comment
  (run-load-test))