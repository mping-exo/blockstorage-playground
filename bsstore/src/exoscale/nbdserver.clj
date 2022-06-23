(ns exoscale.nbdserver
  "Simple NBD server, oldstyle 'negotiation'.
  Informed by https://github.com/amccurry/nbd-server.git"
  (:require [com.stuartsierra.component :as comp]
            [aleph.http :as http])
  (:import [java.io File RandomAccessFile DataOutputStream DataInputStream Closeable BufferedOutputStream]
           [java.net ServerSocket Socket]
           [com.google.common.primitives Longs UnsignedLong UnsignedInteger]))



(defprotocol NBDImpl
  (nbd-max-size [this])
  ;; NBD commands
  (nbd-read [this handle din dout len offset])
  (nbd-write [this handle din dout len offset])
  (nbd-trim [this handle din dout len offset])
  (nbd-flush [this handle din dout])
  (nbd-cache [this handle din dout]))

;; real impl
(defn- -read [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout ^Long len offset])
(defn- -write [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout ^Long len ^Long offset])
(defn- -trim [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout ^Long len ^Long offset])
(defn- -flush [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout])
(defn- -cache [^RandomAccessFile raf handle ^DataInputStream din ^DataOutputStream dout])

(defrecord NbdFileImpl [size ^RandomAccessFile raf]
  NBDImpl
  (nbd-max-size [this] size)
  (nbd-read [this handle din dout len offset] (-read raf handle din dout len offset))
  (nbd-write [this handle din dout len offset] (-write raf handle din dout len offset))
  (nbd-trim [this handle din dout len offset] (-trim raf handle din dout len offset))
  (nbd-flush [this handle din dout] (-flush this handle din dout))
  (nbd-cache [this handle din dout] (-cache raf handle din dout)))

;;;;
;; nbd proto

(def ^"[B" INIT_PASSWD (.getBytes "NBDMAGIC"))
(def ^"[B" OPTS_MAGIC_BYTES (Longs/toByteArray 5280542401877725268))
(def NBD_REQUEST_MAGIC 0x25609513)

(def NBD_FLAG_HAS_FLAGS (bit-shift-left 1 0))
(def NBD_FLAG_SEND_FLUSH (bit-shift-left 1 2))
(def NBD_FLAG_SEND_TRIM (bit-shift-left 1 5))

(defn- handshake [^DataInputStream din ^DataOutputStream dout]
  ;; S: 64 bits, 0x4e42444d41474943 (ASCII 'NBDMAGIC') (also known as the INIT_PASSWD)
  ;; S: 64 bits, 0x00420281861253 (cliserv_magic, a magic number)
  (doto dout
    (.write INIT_PASSWD)
    (.write OPTS_MAGIC_BYTES)
    (.writeShort 1)
    (.flush))

  (let [flags (.readInt din)
        magic (.readLong din)
        opt   (.readInt din)]
    (when (not= 1 opt)
      (throw (IllegalArgumentException. "Only supports NBD_OPT_EXPORT_NAME")))
    (let [len (.readInt din)
          ba  (byte-array len)]
      (.readFully din ba)
      (String. ba))))

(def cmds {0 :read
           1 :write
           2 :disconnect
           3 :flush
           4 :trim
           5 :cache})

;; OLDSTYLE
(defn- handle-connections [^ServerSocket ss impl]
  (let [socket  (.accept ss)
        handle? (atom true)]
    (with-open [din  (DataInputStream. (.getInputStream socket))
                dout (DataOutputStream. (BufferedOutputStream. (.getOutputStream socket)))]
      (let [export (handshake din dout)]
        ;; S: 64 bits, size of the export in bytes (unsigned)
        ;; S: 32 bits, flags
        ;; S: 124 bytes, zeroes (reserved).

        (.writeLong dout (nbd-max-size impl))
        (.writeShort dout (bit-or NBD_FLAG_HAS_FLAGS NBD_FLAG_SEND_FLUSH NBD_FLAG_SEND_TRIM))
        (.write dout (byte-array 124))
        (.flush dout)

        (println "Client accepted: export " export "size" (nbd-max-size impl))

        (while @handle?
          (let [magic (.readInt din)]
            (when (not= magic NBD_REQUEST_MAGIC)
              (throw (IllegalArgumentException. "Invalid magic: " magic)))

            (let [cmd     (.readInt din)
                  handle  (.readLong din)
                  offset  (UnsignedLong/fromLongBits (.readLong din))
                  reqlen  (UnsignedInteger/fromIntBits (.readInt din))
                  cmdname (get cmds cmd)]

              (condp = cmdname
                :read (nbd-read impl handle din dout reqlen offset)
                :write (nbd-write impl handle din dout reqlen offset)
                :disconnect (reset! handle? false)
                :flush (nbd-flush impl handle din dout)
                :trim (nbd-trim impl handle din dout reqlen offset)
                :cache (nbd-cache impl handle din dout)

                (throw (IllegalArgumentException. "Unknown cmd: " cmd))))))))))


(defn- start-ss [listen? port nbd-impl]
  (future
    (with-open [ss (ServerSocket. port)]
      (while @listen?
        (try
          (handle-connections ss nbd-impl)
          (catch Exception e
            (reset! listen? false)
            (.printStackTrace e))))
      (.close ss))))

;; connect: sudo nbd-client -N file/foo localhost 6666 /dev/nbd1 -b 4096 -g
;; mkfs2: sudo mkfs.ext4 -b 4096 /dev/nbd1
;; mount:  sudo mount /dev/nbd1 /mnt/nbd-mount
;; ???
;; profit
(defrecord NBDServer [size impl]
  comp/Lifecycle
  (start [this]
    (let [listen-flag (atom true)]
      (assoc this
        :listen listen-flag
        :fut (start-ss listen-flag 6666 impl))))
  (stop [this]
    (when (:listen this)
      (reset! (:listen this) false))))

(defn make-nbd-server [size]
  (let [f    (File/createTempFile "clj-nbd-server" ".file")
        raf  (RandomAccessFile. f "rw")
        impl (->NbdFileImpl size raf)]
    (->NBDServer size impl)))

(def server (make-nbd-server (* 1024 1024 1024)))

(comp/stop server)
(comp/start server)