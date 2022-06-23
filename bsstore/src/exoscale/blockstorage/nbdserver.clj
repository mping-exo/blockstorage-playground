(ns exoscale.blockstorage.nbdserver
  "Simple NBD server, oldstyle 'negotiation'.
  Informed by https://github.com/amccurry/nbd-server.git"
  (:require [com.stuartsierra.component :as comp]
            [exoscale.blockstorage.protocols :as p]
            [exoscale.blockstorage.nbdfileimpl :as fileimpl])
  (:import [java.io DataOutputStream DataInputStream BufferedOutputStream]
           [java.net ServerSocket]
           [com.google.common.primitives UnsignedLong UnsignedInteger]))

;; oldstyle "negotiation": take it or leave it
(defn- handshake [^DataInputStream din ^DataOutputStream dout]
  ;; S: 64 bits, 0x4e42444d41474943 (ASCII 'NBDMAGIC') (also known as the INIT_PASSWD)
  ;; S: 64 bits, 0x00420281861253 (cliserv_magic, a magic number)
  (doto dout
    (.write p/INIT_PASSWD)
    (.write p/OPTS_MAGIC_BYTES)
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

(defn- handle-connections [^ServerSocket ss handlers]
  (let [socket  (.accept ss)
        handle? (atom true)]
    (with-open [din  (DataInputStream. (.getInputStream socket))
                dout (DataOutputStream. (BufferedOutputStream. (.getOutputStream socket)))]
      (let [export (handshake din dout)]
        ;; S: 64 bits, size of the export in bytes (unsigned)
        ;; S: 32 bits, flags
        ;; S: 124 bytes, zeroes (reserved).

        (.writeLong dout (p/nbd-max-size (first handlers)))
        (.writeShort dout (bit-or p/NBD_FLAG_HAS_FLAGS p/NBD_FLAG_SEND_FLUSH p/NBD_FLAG_SEND_TRIM))
        (.write dout (byte-array 124))
        (.flush dout)

        (println "Client accepted: export " export "size" (p/nbd-max-size (first handlers)))

        (while @handle?
          (let [magic (.readInt din)]
            (when (not= magic p/NBD_REQUEST_MAGIC)
              (throw (IllegalArgumentException. "Invalid magic: " magic)))

            (let [cmd     (.readInt din)
                  handle  (.readLong din)
                  offset  (UnsignedLong/fromLongBits (.readLong din))
                  reqlen  (UnsignedInteger/fromIntBits (.readInt din))
                  cmdname (get cmds cmd)]

              ;; dispatch
              (condp = cmdname
                :read (doseq [impl handlers] (p/nbd-read impl handle din dout reqlen offset))
                :write (doseq [impl handlers] (p/nbd-write impl handle din dout reqlen offset))
                :disconnect (do (println "[disconnect]") (reset! handle? false))
                :flush (doseq [impl handlers] (p/nbd-flush impl handle din dout))
                :trim (doseq [impl handlers] (p/nbd-trim impl handle din dout reqlen offset))
                :cache (doseq [impl handlers] (p/nbd-cache impl handle din dout))

                (throw (IllegalArgumentException. "Unknown cmd: " cmd))))))))))


(defn- start-ss [listen? port handlers]
  (future
    (with-open [ss (ServerSocket. port)]
      (while @listen?
        (try
          (handle-connections ss handlers)
          (catch Exception e
            (reset! listen? false)
            (.printStackTrace e))))
      (.close ss))))

;; connect: sudo nbd-client -N file/foo localhost 6666 /dev/nbd1 -b 4096 -g
;; mkfs2: sudo mkfs.ext4 -b 4096 /dev/nbd1
;; mount:  sudo mount /dev/nbd1 /mnt/nbd-mount
;; ???
;; profit
(defrecord NBDServer [size impl metastore]
  comp/Lifecycle
  (start [this]
    (let [listen-flag (atom true)]
      (assoc this
        :listen listen-flag
        :fut (start-ss listen-flag 6666 [impl metastore]))))
  (stop [this]
    (when (:listen this)
      (reset! (:listen this) false))))

(comment
  (def size (* 1024 1024 1024))
  (def server (->NBDServer size [(fileimpl/make-file-impl size)]))

  (comp/stop server)
  (comp/start server))