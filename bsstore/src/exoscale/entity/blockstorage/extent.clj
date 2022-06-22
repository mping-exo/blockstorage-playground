(ns exoscale.entity.blockstorage.extent
  (:require [clojure.spec.alpha :as s]
            [exoscale.entity.sos.blob :as blob]
            [exoscale.entity.blockstorage.blobview :as bv]
            [exoscale.entity.blockstorage.spec :as spec])
  (:import [java.util UUID]))

(s/def ::uuid uuid?)
(s/def ::disk-offset ::spec/offset)
(s/def ::blob-views (s/coll-of ::bv/blobview))
(s/def ::is-snapshot? boolean?)

(s/def ::extent (s/keys :req [::uuid ::disk-offset ::blob-views]
                  :opt [::is-snapshot?]))

(defn blobs [extent]
  (->> (get extent ::blob-views)
       (map (fn [bv] (get-in bv [::bv/partial-blob ::blob/blob])))))

(defn make-extent [uuid disk-offset blob-views]
  {::uuid         (if (uuid? uuid) uuid (UUID/fromString uuid))
   ::disk-offset  disk-offset
   ::blob-views   blob-views
   ::is-snapshot? false})


(defprotocol ^:no-doc ExtentStore
  "Extent Metadata store"
  (-get-by-offset [store uuid disk-offset] "Gets an extent by its PK")
  (-get-all [store]
    [store uuid]
    "Gets all extends, optionally for a given UUID")
  (-long-range-reduce [store f val start-uuid] "Long range reduce `f` with initial `val` over extends with `start-uuid` uuid")
  (-insert [store extent] "Inserts an extent"))
