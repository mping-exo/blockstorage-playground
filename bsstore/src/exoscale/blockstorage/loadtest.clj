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
  (:use [criterium.core])
  (:import [java.util UUID]))

(defn- insert-extent-tx [store exts]
  (bs/-run-in-transaction store
    (fn [s]
      (doseq [ext exts]
        (extent/-insert s ext)))))

(def ^:private extent-generator (s/gen ::extent/extent))
(def fixed-uuid (UUID/randomUUID))

(defn- rand-extent [uuid disk-offset]
  (-> (g/generate extent-generator)
      (assoc ::extent/uuid uuid)
      (assoc ::extent/disk-offset disk-offset)))

(defn- run-load-test [& args]
  (let [store (-> {:env          "blockstorage"
                   :cluster-file "/etc/foundationdb/fdb.cluster"}
                (bsstore/make-bsstore)
                (component/start))
        batch 10
        rounds 100
        total (atom 0)]
    (dotimes [round rounds]
      (let [s (* round batch)
            e (+ s batch)]
        (println "Inserting  100 random extents [" s "," e "]")
        (insert-extent-tx store (mapv (fn [offset] (rand-extent fixed-uuid offset))
                                      (range s e)))
        (swap! total + (* rounds batch))))
    (println "Inserted total of" @total "Extents")))


(defn -main [& args]
  (run-load-test args))


(comment
  ;; Evaluation count : 864 in 6 samples of 144 calls.
  ;; Execution time sample mean : 830.026994 µs
  ;; Execution time mean : 829.832517 µs
  ;; Execution time sample std-deviation : 125.175964 µs
  ;; Execution time std-deviation : 128.535080 µs
  ;; Execution time lower quantile : 722.593479 µs ( 2.5%)
  ;; Execution time upper quantile : 1.023018 ms (97.5%)
  ;; Overhead used : 4.032115 ns
  (with-progress-reporting (quick-bench (rand-extent) :verbose))

  (def store (-> {:env          "blockstorage"
                  :cluster-file "/etc/foundationdb/fdb.cluster"}
               (bsstore/make-bsstore)
               (component/start)))

  (run-load-test))