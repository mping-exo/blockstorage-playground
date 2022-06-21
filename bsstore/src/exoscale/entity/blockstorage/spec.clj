(ns exoscale.entity.blockstorage.spec)

(defn pos-num?
  "Return true if x is a positive number"
  {:added "1.9"}
  [x] (and (number? x)
           (pos? x)))
