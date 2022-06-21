(ns exoscale.blockstorage.bsstore
  "Implementation of the exoscale.entity.sos/Metastore protocol
   on top of fdb-record-layer"
  (:require [clojure.spec.alpha :as s]
            [clojure.repl :refer [demunge]]
            [clojure.string :as str]
            [com.stuartsierra.component :as component]
            [exoscale.entity.blockstorage :as bs]
            [exoscale.blockstorage.bsstore.payload :as p]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.vinyl.query :as query]
            [exoscale.vinyl.store :as store]
            [telemetric-clj.macros :refer [with-span-attrs]])
  (:import exoscale.blockstorage.BSStore))

;; TODO use params?
(defn ^:no-doc ^:private extents-by-uuid-and-offset [uuid disk-offset]
  (query/build-query
    :Extent [:and
             [:= :uuid (str uuid)]
             [:= :diskOffset disk-offset]]))

;; First we define primary keys and indices for the metastore
(def ^:private schema
  {:RefCountedBlob
   {:primary-key [:concat :type-key
                  [:nested "blob" "partition"]
                  [:nested "blob" "blob_id"]]}

   :Extent
   {:primary-key [:concat :type-key
                  "uuid"
                  "diskOffset"]
    :indices     [{:name "uuid"
                   :on   :uuid}]}})

(defrecord VinylMetastore [store])

(extend-type VinylMetastore
  extent/ExtentStore
  (-get-by-offset [this uuid disk-offset]
    (store/list-query (:store this)
      (extents-by-uuid-and-offset uuid disk-offset)
      {::store/transform p/parse-record}))
  (-get-all
    ([this]
     (store/list-query (:store this)
       (query/build-query :Extent)
       {::store/transform p/parse-record}))
    ([this uuid]
     (store/list-query (:store this)
       (query/build-query :Extent [:and [:= :uuid uuid]])
       {::store/transform p/parse-record})))
  (-insert [this extent]
    (store/insert-record (:store this)
      (p/map->record :Extent extent))))

;;;;
;; store def

(defn build-bsstore [store] (->VinylMetastore store))

(defn run-store-fn
  [db f]
  (store/run-in-context
    db
    (fn [store]
      (f (build-bsstore store)))))

(defn scan-properties [{:keys [marker delimiter max-keys]}]
  (cond-> {}
    (seq marker) (assoc ::store/continuation [marker])
    ;; If there is no `delimiter`, we don't have to fetch all the objects to determine the prefixes.
    ;; We just get the `limit`, nothing more.
    ;; When a marker is present, we need to find one more object
    ;; Note that we need to fetch a least one object when the `limit` is 0 to determine the next `marker`.
    (empty? delimiter) (assoc ::store/limit
                         (cond-> (max max-keys 1)
                           (some? marker) inc))))

(defn- function->str [f]
  (let [s (str f)
        s (demunge s)
        [label] (str/split s #"@")]
    label))

;;;;
;; main component
(defrecord VinylTransactor [env db]
  component/Lifecycle
  (start [this]
    (let [db (store/start
               (store/initialize :bsstore
                 (BSStore/getDescriptor)
                 schema
                 (select-keys this [:env :cluster-file :schema-name :schema :descriptor])))]
      (assoc this :db db)))
  (stop [this]
    (when (some? db)
      (store/stop db))
    (assoc this :db nil))
  bs/AtomicMetastore
  (-run-in-transaction [_ f]
    (do                                                     ;with-span-attrs "metastore/run-in-transaction" {:nest? false :attrs {:function (function->str f)}}
      (run-store-fn db f)))
  (-reduce-extents [_ uuid reducer init opts]
    (do                                                     ;with-span-attrs "metastore/reduce-keys" {:nest? false}
      (store/long-range-transduce db
        (map p/parse-record)
        (completing reducer)
        init
        :Extent [uuid]
        (scan-properties opts)))))

(defn make-bsstore
  "A store configured for holding object storage metadata. Implements the
  following protocols:

  - exoscale.entity.sos/AtomicMetastore

  Can be configured with the following keys:

  |       Option |                                                                          Value |
  |:-------------|:-------------------------------------------------------------------------------|
  |          env | Name of the environment, defaults to a generated name starting with `testing_` |
  | cluster-file | Alternative location for the FDB cluster-file, defaults to the system location |"
  ([{:keys [env cluster-file]}]
   (map->VinylTransactor {:env          env
                          :cluster-file cluster-file
                          :schema-name  :metastore
                          :descriptor   (BSStore/getDescriptor)
                          :schema       schema})))

(s/def ::bsstore (partial satisfies? bs/AtomicMetastore))
