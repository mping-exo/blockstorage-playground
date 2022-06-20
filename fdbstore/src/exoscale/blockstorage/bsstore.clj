(ns exoscale.blockstorage.bsstore
  "Implementation of the exoscale.entity.sos/Metastore protocol
   on top of fdb-record-layer"
  (:require [clojure.spec.alpha :as s]
            [clojure.repl :refer [demunge]]
            [clojure.string :as str]
            [com.stuartsierra.component :as component]
            [exoscale.entity.sos :as sos]
            [exoscale.blockstorage.bsstore.payload :as p]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.vinyl.query :as query]
            [exoscale.vinyl.store :as store]
            [telemetric-clj.macros :refer [with-span-attrs]])
  (:import exoscale.blockstorage.BSStore
           [java.util UUID]))

(defprotocol ^:no-doc AtomicMetastore
  "The main protocol the underlying transactables should implement"
  (-reduce-extents [this uuid reducer init opts]
    "Reduce over extents with a transformation of f(xf).")
  (-run-in-transaction [this f]
    "Run `f` over an implementation of all known metadata protocols:
     - `extent/ExtentStore`"))

(defmacro with-transaction
  "Perform provided in the context of a transaction against the store"
  [[sym store] & fntail]
  `(-run-in-transaction ~store (fn [~sym] ~@fntail)))


(defn ^:no-doc ^:private extends-by-uuid-and-offset [uuid disk-offset]
  (query/build-query
    :Extent [:and
             [:= :uuid uuid]
             [:= :diskOffset disk-offset]
             [:= :isSnapshot false]]))


;; First we define primary keys and indices for the metastore
(def ^:private schema
  {:Extent
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
      (extends-by-uuid-and-offset uuid disk-offset)
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
;;

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
  AtomicMetastore
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

(s/def ::bsstore (partial satisfies? AtomicMetastore))

(comment
  (def store (->
               {:env          "bsstore"
                :cluster-file "/etc/foundationdb/fdb.cluster"}
               (make-bsstore)
               (component/start)))

  @(-run-in-transaction store
     (fn [s] (extent/-get-by-offset s "" 0)))

  @(-run-in-transaction store
     (fn [s] (extent/-get-all s)))

  (-run-in-transaction store
    (fn [s] (extent/-insert s (extent/make-extent (str (UUID/randomUUID)) 0 []))))

  "")
