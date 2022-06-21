(ns exoscale.entity.blockstorage.extent
  (:require [clojure.spec.alpha :as s]
            [exoscale.entity.blockstorage.blobview :as bv]
            [exoscale.entity.blockstorage.spec :as spec])
  (:import [java.util UUID]))

(s/def ::uuid uuid?)
(s/def ::disk-offset ::spec/offset)
(s/def ::blob-views (s/coll-of ::bv/blobview))
(s/def ::is-snapshot? boolean?)

(s/def ::extent (s/keys :req [::uuid ::disk-offset ::blob-views]
                        :opt [::is-snapshot?]))

(defn make-extent [uuid disk-offset blob-views]
  {::uuid         (if (uuid? uuid) uuid (UUID/fromString uuid))
   ::disk-offset  disk-offset
   ::blob-views   blob-views
   ::is-snapshot? false})


(defprotocol ^:no-doc ExtentStore
  "Extent Metadata store"
  (-get-by-offset [store uuid disk-offset])
  (-get-all [store]
    [store uuid])
  (-insert [store extent]))
