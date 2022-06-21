(ns exoscale.entity.blockstorage.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g]
            [clojure.test.check.generators :as test.g]))

(defn abs [^long v] (Math/abs v))

(s/def ::offset (s/with-gen number? #(test.g/fmap abs (s/gen int?))))