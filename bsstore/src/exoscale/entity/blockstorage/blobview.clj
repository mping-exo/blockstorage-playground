(ns exoscale.entity.blockstorage.blobview
  (:require [clojure.spec.alpha :as s]
            [exoscale.entity.blockstorage.rcblob :as rcblob]
            [exoscale.entity.blockstorage.spec :as spec]))

(s/def ::rcblob ::rcblob/rcblob)
(s/def ::blob-offset ::spec/offset)
(s/def ::blob-size ::spec/offset)
(s/def ::extent-offset ::spec/offset)
(s/def ::blobview (s/keys :req [::rcblob ::blob-offset ::blob-size ::extent-offset]))

(defn make-blobview [rcblob blob-offset blob-size extent-offset]
  {::rcblob        rcblob
   ::blob-offset   blob-offset
   ::blob-size     blob-size
   ::extent-offset extent-offset})
