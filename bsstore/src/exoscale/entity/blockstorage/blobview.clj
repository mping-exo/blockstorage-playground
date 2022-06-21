(ns exoscale.entity.blockstorage.blobview
  (:require [clojure.spec.alpha :as s]
            [exoscale.entity.sos.blob :as blob]
            [exoscale.entity.blockstorage.spec :as spec]))

(s/def ::partial-blob ::blob/partial)
(s/def ::extent-offset ::spec/offset)
(s/def ::blobview (s/keys :req [::partial-blob ::extent-offset]))

(defn make-blobview [partial-blob extent-offset]
  {::partial-blob  partial-blob
   ::extent-offset extent-offset})
