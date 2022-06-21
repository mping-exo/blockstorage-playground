(ns bsstore.core-test
  (:require [clojure.test :refer :all]
            [exoscale.vinyl.store :as store]
            [exoscale.blockstorage.bsstore :as bsstore]
            [com.stuartsierra.component :as component]
            [exoscale.entity.blockstorage :as bs]
            [exoscale.entity.blockstorage.extent :as extent])
  (:import [com.apple.foundationdb.record.query.plan.plans RecordQueryPlan]
           [java.util UUID]))

;; copied from exoscale.sos.metastore-queries-test

(defn check-no-fullscan [^RecordQueryPlan plan]
  (println "Plan:" (.toString plan))
  (println " - hasFullRecordScan:" (.hasFullRecordScan plan))
  (println " - index count?:" (.size (.getUsedIndexes plan)))
  (println " - hasLoadBykeys?:" (.hasLoadBykeys plan))
  (is (not (.hasFullRecordScan plan))))


(deftest table-scans-test
  (let [orig    store/store-query-fn
        proxied (fn proxied-store-query-fn
                  [query {::store/keys [intercept-plan-fn] :as opts}]
                  (when (some? intercept-plan-fn)
                    (throw (RuntimeException. "Cannot override existin `intercept-plan-fn`")))
                  (orig query (assoc opts ::store/intercept-plan-fn check-no-fullscan)))]

    (testing "Full Table Scans"
      (with-redefs [store/store-query-fn proxied]
        (let [fdb-cluster (-> (bsstore/make-bsstore {:cluster-file "/etc/foundationdb/fdb.cluster"
                                                     :env          "blockstorage"})
                            (component/start))]
          (testing "Extent"
            @(bs/-run-in-transaction fdb-cluster
               (fn [s] (extent/-get-by-offset s (UUID/randomUUID) 10)))))))))

