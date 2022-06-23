(ns exoscale.blockstorage.nbdfileimpl
  (:require [exoscale.blockstorage.protocols :as p])
  (:import [java.io RandomAccessFile DataInputStream DataOutputStream File]))

;; real impl, no bounds check :D
(defn- -read [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout ^Long len offset]
  (println "[read]" offset len)
  (let [buffer (byte-array len)]
    (locking raf
      (.seek raf offset)
      (.read raf buffer))
    (.write dout p/NBD_REPLY_MAGIC_BYTES)
    (.write dout p/NBD_OK_BYTES)
    (.writeLong dout handle)
    (.write dout buffer)
    (.flush dout)))

(defn- -write [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout ^Long len ^Long offset]
  (println "[read]" offset len)
  (let [buffer (byte-array len)]
    (locking raf
      (.readFully din buffer)
      (.seek raf offset)
      (.write raf buffer))
    (.write dout p/NBD_REPLY_MAGIC_BYTES)
    (.write dout p/NBD_OK_BYTES)
    (.writeLong dout handle)
    (.flush dout)))

(defn- -trim [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout ^Long len ^Long offset]
  (println "[trim]" offset len)
  ;; lazy: just write a big ass buffer
  (let [buf (byte-array len)]
    (locking raf
      (.seek raf offset)
      (.write raf buf)))
  (.write dout p/NBD_REPLY_MAGIC_BYTES)
  (.write dout p/NBD_OK_BYTES)
  (.writeLong dout handle)
  (.flush dout))

(defn- -flush [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout]
  (println "[flush]")
  (locking raf
    (.sync (.getFD raf)))
  (.write dout p/NBD_REPLY_MAGIC_BYTES)
  (.write dout p/NBD_OK_BYTES)
  (.writeLong dout handle)
  (.flush dout))

(defn- -cache [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout]
  (println "[cache]"))

(defrecord NbdFileImpl [size ^RandomAccessFile raf]
  p/NBDImpl
  (nbd-max-size [this]
    size)
  (nbd-read [this handle din dout len offset]
    (-read raf handle din dout len offset))
  (nbd-write [this handle din dout len offset]
    (-write raf handle din dout len offset))
  (nbd-trim [this handle din dout len offset]
    (-trim raf handle din dout len offset))
  (nbd-flush [this handle din dout]
    (-flush raf handle din dout))
  (nbd-cache [this handle din dout]
    (-cache raf handle din dout)))

(defn make-file-impl [size]
  (let [f    (File/createTempFile "clj-nbd-server" ".file")
        raf  (RandomAccessFile. f "rw")]
    (.deleteOnExit f)
    (.setLength raf size)

    (println "Using backing file:" (.getAbsolutePath f))
    (->NbdFileImpl size raf)))
