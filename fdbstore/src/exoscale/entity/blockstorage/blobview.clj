(ns exoscale.entity.blockstorage.blobview
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [exoscale.entity.blockstorage.rcblob :as rcblob]))

(defn make-blobview [rcblob blob-offset blob-size extent-offset]
  {::rcblob        rcblob
   ::blob-offset   blob-offset
   ::blob-size     blob-size
   ::extent-offset extent-offset})
