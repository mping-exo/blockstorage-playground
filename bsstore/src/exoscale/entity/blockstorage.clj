(ns exoscale.entity.blockstorage
  (:require [clojure.spec.alpha :as s]
            [exoscale.entity.blockstorage.extent :as ex]
            [exoscale.entity.blockstorage.blobview :as bv]
            [exoscale.entity.blockstorage.rcblob :as rc]))

(s/def ::rcblob ::rc/rcblob)
(s/def ::blobview ::bv/blobview)
(s/def ::extent ::ex/extent)

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
