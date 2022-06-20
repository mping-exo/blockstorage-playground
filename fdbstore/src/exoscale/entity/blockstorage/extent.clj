(ns exoscale.entity.blockstorage.extent
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [exoscale.entity.blockstorage.rcblob :as rcblob]
            [exoscale.entity.blockstorage.blobview :as blobview])
  (:import [java.util UUID]))

(defn make-extent [uuid disk-offset blob-views]
  {::uuid        (if (uuid? uuid) uuid (UUID/fromString uuid))
   ::disk-offset disk-offset
   ::blob-views  blob-views})


(defprotocol ^:no-doc ExtentStore
  "Extent Metadata store"
  (-get-by-offset [store uuid disk-offset])
  (-get-all [store]
            [store uuid])
  (-insert [store extent]))
