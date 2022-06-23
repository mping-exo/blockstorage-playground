(ns exoscale.blockstorage.protocols
  (:import [com.google.common.primitives Longs Ints]))

;;;;
;; nbd constants
(def ^"[B" INIT_PASSWD (.getBytes "NBDMAGIC"))
(def ^"[B" OPTS_MAGIC_BYTES (Longs/toByteArray 5280542401877725268))
(def NBD_REQUEST_MAGIC 0x25609513)
(def ^"[B" NBD_REPLY_MAGIC_BYTES (Ints/toByteArray 1732535960))
(def ^"[B" NBD_OK_BYTES (byte-array 4))

(def NBD_FLAG_HAS_FLAGS (bit-shift-left 1 0))
(def NBD_FLAG_SEND_FLUSH (bit-shift-left 1 2))
(def NBD_FLAG_SEND_TRIM (bit-shift-left 1 5))


(defprotocol NBDImpl
  (nbd-max-size [this])
  ;; NBD commands
  (nbd-read [this handle din dout len offset])
  (nbd-write [this handle din dout len offset])
  (nbd-trim [this handle din dout len offset])
  (nbd-flush [this handle din dout])
  (nbd-cache [this handle din dout]))
