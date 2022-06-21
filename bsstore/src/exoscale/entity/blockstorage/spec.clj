(ns exoscale.entity.blockstorage.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g]
            [clojure.test.check.generators :as test.g]))


(defn pos-num?
  "Return true if x is a positive number"
  {:added "1.9"}
  [x] (and (number? x)
           (pos? x)))

(defn abs [^long v] (Math/abs v))
