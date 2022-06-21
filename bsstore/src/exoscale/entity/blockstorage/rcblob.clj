(ns exoscale.entity.blockstorage.rcblob
  (:require [clojure.spec.alpha :as s]
            [exoscale.entity.sos.blob :as blob]))

(s/def ::refcount nat-int?)
(s/def ::blob ::blob/blob)
(s/def ::rcblob (s/keys :req [::blob ::refcount]))

(defn make-rcblob [blob refcount]
  {::blob     blob
   ::refcount refcount})
