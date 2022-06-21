(ns exoscale.blockstorage.bsstore.payload
  "Welcome to the FoundationDB record-layer's dark side. We're out of 🍪.
   Since the record layer relies on protobuf and we want to expose maps
   which conform to the entity specs, we're bound to have a translation
   namespace.

   The functions here are explicit to avoid reflection or going through
   message descriptors.

   If you change stuff here, don't forget to update the generative tests
   in `payload_test.clj` as well."
  (:require [exoscale.entity.sos :as sos]
            [exoscale.entity.blockstorage :as blockstorage]
            [exoscale.entity.sos.blob :as blob]
            [exoscale.entity.blockstorage.blobview :as blobview]
            [exoscale.entity.blockstorage.rcblob :as rcblob]
            [exoscale.entity.blockstorage.extent :as extent]
            [exoscale.ex :as ex]
            [clojure.string :as str])
  (:import com.apple.foundationdb.record.provider.foundationdb.FDBRecord
           com.google.protobuf.Message
           exoscale.sos.BlobProto$Blob
           exoscale.sos.BlobProto$BlobOrBuilder
           exoscale.blockstorage.BSStore$RefCountedBlob
           exoscale.blockstorage.BSStore$RefCountedBlobOrBuilder
           exoscale.blockstorage.BSStore$BlobView
           exoscale.blockstorage.BSStore$BlobViewOrBuilder
           exoscale.blockstorage.BSStore$Extent
           exoscale.blockstorage.BSStore$ExtentOrBuilder
           [exoscale.blockstorage BSStore$RefCountedBlob BSStore$RefCountedBlobOrBuilder BSStore$BlobViewOrBuilder BSStore$ExtentOrBuilder BSStore$RefCountedBlob$Builder BSStore$BlobView$Builder BSStore$Extent$Builder]
           [java.util UUID]
           [exoscale.sos BlobProto$PartialBlob BlobProto$PartialBlobOrBuilder]))


(defprotocol RecordParser
  (parse-record [this]
    "Attempts to yield a correctly deserialized map from several types of
     inputs. When querying the FDB record-layer, we get back a `FDBRecord`
     instance.

     These instance hold both a raw protobuf message and a record type
     reference. In this case we dispatch on the appropriate type. Otherwise
     we expect to find a concrete protobuf type as defined in our schema."))

(defprotocol ^:no-doc RecordMerger
  (merge-from [this other]
    "Merging from a raw protobuf message into a builder"))

(defrecord RecordInfo [type record]
  RecordParser
  (parse-record [_]
    (case type
      "RefCountedBlob" (-> (merge-from (BSStore$RefCountedBlob/newBuilder) record)
                         parse-record)
      "BlobView" (-> (merge-from (BSStore$BlobView/newBuilder) record)
                   parse-record)
      "Extent" (-> (merge-from (BSStore$Extent/newBuilder) record)
                 parse-record))))

(defn- ^UUID as-uuid
  [^String s]
  (when-not (str/blank? s)
    (UUID/fromString s)))

;; TODO whis was copied from metastore, we could import the whole metastore module
(defn ^BlobProto$Blob blob->record
  "Serialize a blob to protobuf"
  [{::blob/keys [partition id size] :as blob}]
  (ex/assert-spec-valid ::sos/blob blob)
  (let [b (BlobProto$Blob/newBuilder)]
    (.setPartition b (int partition))
    (.setBlobId b (long id))
    (.setSize b (int size))
    (.build b)))

(defn ^BlobProto$PartialBlob partial-blob->record
  [{::blob/keys [blob offset length]}]
  (-> (BlobProto$PartialBlob/newBuilder)
    (.setBlob (blob->record blob))
    (.setOffset (int offset))
    (.setLength (int length))
    (.build)))

(defn ^BSStore$RefCountedBlob rcblob->record
  "Serialize an refcounted blob to protobuf"
  [{::rcblob/keys [blob refcount] :as rcblob}]
  (ex/assert-spec-valid ::blockstorage/rcblob rcblob)
  (let [b (BSStore$RefCountedBlob/newBuilder)]
    (.setBlob b (blob->record blob))
    (.setRefcount b refcount)
    (.build b)))

(defn ^BSStore$BlobView blobview->record
  "Serialize an refcounted blob to protobuf"
  [{::blobview/keys [partial-blob extent-offset] :as blobview}]
  (ex/assert-spec-valid ::blockstorage/blobview blobview)
  (let [b (BSStore$BlobView/newBuilder)]
    (.setPartialBlob b (partial-blob->record partial-blob))
    (.setExtentOffset b extent-offset)
    (.build b)))

(defn ^BSStore$Extent extent->record
  "Serialize an refcounted blob to protobuf"
  [{::extent/keys [uuid disk-offset blob-views is-snapshot?] :as extent}]
  (ex/assert-spec-valid ::blockstorage/extent extent)
  (let [b (BSStore$Extent/newBuilder)]
    (.setUuid b (str uuid))
    (.setDiskOffset b disk-offset)
    (.setIsSnapshot b (or is-snapshot? false))
    (.addAllBlobViews b (mapv blobview->record blob-views))
    (.build b)))


(defn ^Message map->record
  "Convert a map to protobuf. The first argument names
   the type to convert to.

  This is only implemented for queryable records, others will
  get serialized transitively."
  [record-type m]
  (case record-type
    :RefCountedBlob (rcblob->record m)
    ;; :BlobView (blobview->record m)
    :Extent (extent->record m)))

(defn record->blob
  "Protobuf blob to a blob map"
  [^BlobProto$BlobOrBuilder payload]
  #::blob{:id        (.getBlobId payload)
          :partition (.getPartition payload)
          :size      (.getSize payload)})

(defn record->partial-blob
  "Protobuf blob to a blob map"
  [^BlobProto$PartialBlob payload]
  #::blob{:blob   (record->blob (.getBlob payload))
          :offset (.getOffset payload)
          :length (.getLength payload)})

(defn record->rcblob
  "Protobuf inode to an inode map"
  [^BSStore$RefCountedBlobOrBuilder payload]
  #::rcblob{:blob     (.getBlob payload)
            :refcount (.getRefcount payload)})

(defn record->blobview
  [^BSStore$BlobViewOrBuilder payload]
  #::extent{::partial-blob  (record->partial-blob (.getPartialBlob payload))
            ::extent-offset (.getExtentOffset payload)})

(defn record->extent
  [^BSStore$ExtentOrBuilder payload]
  #::extent{:uuid         (UUID/fromString (.getUuid payload))
            :disk-offset  (.getDiskOffset payload)
            :blob-views   (mapv record->blobview (.getBlobViewsList payload))
            :is-snapshot? (.getIsSnapshot payload)})

;; Mostly mechanical implementations of the protocols defined up top

(extend-protocol RecordMerger
  BSStore$RefCountedBlob$Builder
  (merge-from [x y] (.mergeFrom x ^Message y))
  BSStore$BlobView$Builder
  (merge-from [x y] (.mergeFrom x ^Message y))
  BSStore$Extent$Builder
  (merge-from [x y] (.mergeFrom x ^Message y)))

(extend-protocol RecordParser
  nil
  (parse-record [r]
    nil)
  FDBRecord
  (parse-record [r]
    (parse-record (RecordInfo. (-> r .getRecordType .getName) (.getRecord r))))
  BSStore$RefCountedBlob
  (parse-record [r] (record->rcblob r))
  BSStore$BlobViewOrBuilder
  (parse-record [r] (record->blobview r))
  BSStore$ExtentOrBuilder
  (parse-record [r] (record->extent r)))

