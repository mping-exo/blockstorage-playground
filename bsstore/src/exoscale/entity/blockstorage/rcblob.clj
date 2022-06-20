(ns exoscale.entity.blockstorage.rcblob
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [exoscale.entity.sos.blob :as blob]))

(defn make-rcblob [blob refcount]
  {::blob blob
   ::refcount refcount})
