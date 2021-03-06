//
// COPIED FROM https://github.com/exoscale/sos/blob/master/proto/rpc.proto
// we should find a way for it to be in sync (eg: git submodules, etc)
//

// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.28.0
// 	protoc        (unknown)
// source: rpc.proto

package proto

import (
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	timestamppb "google.golang.org/protobuf/types/known/timestamppb"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

//
// Supported digest types
type DigestType int32

const (
	DigestType_XHH64 DigestType = 0
	DigestType_MD5   DigestType = 1
)

// Enum value maps for DigestType.
var (
	DigestType_name = map[int32]string{
		0: "XHH64",
		1: "MD5",
	}
	DigestType_value = map[string]int32{
		"XHH64": 0,
		"MD5":   1,
	}
)

func (x DigestType) Enum() *DigestType {
	p := new(DigestType)
	*p = x
	return p
}

func (x DigestType) String() string {
	return protoimpl.X.EnumStringOf(x.Descriptor(), protoreflect.EnumNumber(x))
}

func (DigestType) Descriptor() protoreflect.EnumDescriptor {
	return file_rpc_proto_enumTypes[0].Descriptor()
}

func (DigestType) Type() protoreflect.EnumType {
	return &file_rpc_proto_enumTypes[0]
}

func (x DigestType) Number() protoreflect.EnumNumber {
	return protoreflect.EnumNumber(x)
}

// Deprecated: Use DigestType.Descriptor instead.
func (DigestType) EnumDescriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{0}
}

//
// Blob state in the index DB.
type BlobState int32

const (
	BlobState_COMMITTED BlobState = 0
	BlobState_DELETED   BlobState = 1
)

// Enum value maps for BlobState.
var (
	BlobState_name = map[int32]string{
		0: "COMMITTED",
		1: "DELETED",
	}
	BlobState_value = map[string]int32{
		"COMMITTED": 0,
		"DELETED":   1,
	}
)

func (x BlobState) Enum() *BlobState {
	p := new(BlobState)
	*p = x
	return p
}

func (x BlobState) String() string {
	return protoimpl.X.EnumStringOf(x.Descriptor(), protoreflect.EnumNumber(x))
}

func (BlobState) Descriptor() protoreflect.EnumDescriptor {
	return file_rpc_proto_enumTypes[1].Descriptor()
}

func (BlobState) Type() protoreflect.EnumType {
	return &file_rpc_proto_enumTypes[1]
}

func (x BlobState) Number() protoreflect.EnumNumber {
	return protoreflect.EnumNumber(x)
}

// Deprecated: Use BlobState.Descriptor instead.
func (BlobState) EnumDescriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{1}
}

//
// A blob target, identified by partition and ID.
// Since blob IDs are timestamped IDs will generally
// be large, which voids any benefits gained from using
// variable length integers, we use a fixed width version
// to account for that.
//
// The size field is only useful when used in metadata
type Blob struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Partition int32 `protobuf:"varint,1,opt,name=partition,proto3" json:"partition,omitempty"`
	BlobId    int64 `protobuf:"fixed64,2,opt,name=blob_id,json=blobId,proto3" json:"blob_id,omitempty"`
	Size      int32 `protobuf:"varint,3,opt,name=size,proto3" json:"size,omitempty"`
}

func (x *Blob) Reset() {
	*x = Blob{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Blob) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Blob) ProtoMessage() {}

func (x *Blob) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Blob.ProtoReflect.Descriptor instead.
func (*Blob) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{0}
}

func (x *Blob) GetPartition() int32 {
	if x != nil {
		return x.Partition
	}
	return 0
}

func (x *Blob) GetBlobId() int64 {
	if x != nil {
		return x.BlobId
	}
	return 0
}

func (x *Blob) GetSize() int32 {
	if x != nil {
		return x.Size
	}
	return 0
}

//
// A blob target, with a range to fetch inside the blob.
type PartialBlob struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Blob   *Blob `protobuf:"bytes,1,opt,name=blob,proto3" json:"blob,omitempty"`
	Offset int32 `protobuf:"varint,2,opt,name=offset,proto3" json:"offset,omitempty"`
	Length int32 `protobuf:"varint,3,opt,name=length,proto3" json:"length,omitempty"`
}

func (x *PartialBlob) Reset() {
	*x = PartialBlob{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *PartialBlob) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*PartialBlob) ProtoMessage() {}

func (x *PartialBlob) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use PartialBlob.ProtoReflect.Descriptor instead.
func (*PartialBlob) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{1}
}

func (x *PartialBlob) GetBlob() *Blob {
	if x != nil {
		return x.Blob
	}
	return nil
}

func (x *PartialBlob) GetOffset() int32 {
	if x != nil {
		return x.Offset
	}
	return 0
}

func (x *PartialBlob) GetLength() int32 {
	if x != nil {
		return x.Length
	}
	return 0
}

type Checksum struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Type DigestType `protobuf:"varint,1,opt,name=type,proto3,enum=exoscale.sos.DigestType" json:"type,omitempty"` // digest type
	// size of the blob, 16MB maximum
	Size int32 `protobuf:"varint,2,opt,name=size,proto3" json:"size,omitempty"`
	// 64 bit digest value for the blob
	// In case of MD5 (which is 128 bit), this contains the
	// first 64 bits of the hash
	Digest int64 `protobuf:"varint,3,opt,name=digest,proto3" json:"digest,omitempty"`
}

func (x *Checksum) Reset() {
	*x = Checksum{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[2]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Checksum) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Checksum) ProtoMessage() {}

func (x *Checksum) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[2]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Checksum.ProtoReflect.Descriptor instead.
func (*Checksum) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{2}
}

func (x *Checksum) GetType() DigestType {
	if x != nil {
		return x.Type
	}
	return DigestType_XHH64
}

func (x *Checksum) GetSize() int32 {
	if x != nil {
		return x.Size
	}
	return 0
}

func (x *Checksum) GetDigest() int64 {
	if x != nil {
		return x.Digest
	}
	return 0
}

//
// When doing a Get we return a stream of chunks.
// The first message in the stream *must* contain a checksum.
// This allows the replication and sos to use the same request.
//
//     [Data, Checksum] -> [Data]
type GetChunk struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Data     []byte    `protobuf:"bytes,1,opt,name=data,proto3" json:"data,omitempty"`
	Checksum *Checksum `protobuf:"bytes,2,opt,name=checksum,proto3" json:"checksum,omitempty"`
}

func (x *GetChunk) Reset() {
	*x = GetChunk{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[3]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetChunk) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetChunk) ProtoMessage() {}

func (x *GetChunk) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[3]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetChunk.ProtoReflect.Descriptor instead.
func (*GetChunk) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{3}
}

func (x *GetChunk) GetData() []byte {
	if x != nil {
		return x.Data
	}
	return nil
}

func (x *GetChunk) GetChecksum() *Checksum {
	if x != nil {
		return x.Checksum
	}
	return nil
}

//
// GetPartial returns a stream of chunks.
// Since we don't see a use for it, no Checksum is returned.
// We have used a dedicated type to allow for future extension.
type GetPartialChunk struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Data []byte `protobuf:"bytes,1,opt,name=data,proto3" json:"data,omitempty"`
}

func (x *GetPartialChunk) Reset() {
	*x = GetPartialChunk{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[4]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetPartialChunk) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetPartialChunk) ProtoMessage() {}

func (x *GetPartialChunk) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[4]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetPartialChunk.ProtoReflect.Descriptor instead.
func (*GetPartialChunk) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{4}
}

func (x *GetPartialChunk) GetData() []byte {
	if x != nil {
		return x.Data
	}
	return nil
}

//
// Every request in a blob put operation is a blob chunk.
// The first message in the stream *must* contain a Blob,
// to indicate which blob to store data for.
//
// If the first message in the stream contains a checksum,
// a check for an existing blob with the same checksum is performed,
// immediately returning if it is found.
//
// It is fine to send all fields at once for small payloads, a typical
// sequence for larger uploads will likely look like:
//
//     [Blob, Data] -> [Data] -> [Data] -> [Data,Checksum]
//
// Since replication may try to reupload existing blobs, upload should
// be split in first Blob and Checksum, then the full blob to allow
// the server to discard incoming data when the blob already is present
// on disk:
//
//     [Blob, Checksum] -> [Data]
type PutChunk struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Data     []byte    `protobuf:"bytes,1,opt,name=data,proto3" json:"data,omitempty"`
	Checksum *Checksum `protobuf:"bytes,2,opt,name=checksum,proto3" json:"checksum,omitempty"`
	Blob     *Blob     `protobuf:"bytes,3,opt,name=blob,proto3" json:"blob,omitempty"`
}

func (x *PutChunk) Reset() {
	*x = PutChunk{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[5]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *PutChunk) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*PutChunk) ProtoMessage() {}

func (x *PutChunk) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[5]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use PutChunk.ProtoReflect.Descriptor instead.
func (*PutChunk) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{5}
}

func (x *PutChunk) GetData() []byte {
	if x != nil {
		return x.Data
	}
	return nil
}

func (x *PutChunk) GetChecksum() *Checksum {
	if x != nil {
		return x.Checksum
	}
	return nil
}

func (x *PutChunk) GetBlob() *Blob {
	if x != nil {
		return x.Blob
	}
	return nil
}

// Nothing interesting here, but a type for future compatibility
type PutResult struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *PutResult) Reset() {
	*x = PutResult{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[6]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *PutResult) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*PutResult) ProtoMessage() {}

func (x *PutResult) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[6]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use PutResult.ProtoReflect.Descriptor instead.
func (*PutResult) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{6}
}

// Nothing interesting here, but a type for future compatibility
type DeleteResult struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *DeleteResult) Reset() {
	*x = DeleteResult{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[7]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *DeleteResult) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*DeleteResult) ProtoMessage() {}

func (x *DeleteResult) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[7]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use DeleteResult.ProtoReflect.Descriptor instead.
func (*DeleteResult) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{7}
}

//
// Request to get Blobs metadata from a partiton
// at a given partition offset.
type GetPartitionMetadataRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Partition int32 `protobuf:"varint,1,opt,name=partition,proto3" json:"partition,omitempty"`
	Offset    int64 `protobuf:"varint,2,opt,name=offset,proto3" json:"offset,omitempty"`
}

func (x *GetPartitionMetadataRequest) Reset() {
	*x = GetPartitionMetadataRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[8]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetPartitionMetadataRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetPartitionMetadataRequest) ProtoMessage() {}

func (x *GetPartitionMetadataRequest) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[8]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetPartitionMetadataRequest.ProtoReflect.Descriptor instead.
func (*GetPartitionMetadataRequest) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{8}
}

func (x *GetPartitionMetadataRequest) GetPartition() int32 {
	if x != nil {
		return x.Partition
	}
	return 0
}

func (x *GetPartitionMetadataRequest) GetOffset() int64 {
	if x != nil {
		return x.Offset
	}
	return 0
}

//
// BlobMeta represents a blob metadata from
// the index DB
type BlobMeta struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Blob   *Blob                  `protobuf:"bytes,1,opt,name=blob,proto3" json:"blob,omitempty"`
	Offset int64                  `protobuf:"varint,2,opt,name=offset,proto3" json:"offset,omitempty"`
	State  BlobState              `protobuf:"varint,3,opt,name=state,proto3,enum=exoscale.sos.BlobState" json:"state,omitempty"`
	Dtime  *timestamppb.Timestamp `protobuf:"bytes,4,opt,name=dtime,proto3,oneof" json:"dtime,omitempty"`
}

func (x *BlobMeta) Reset() {
	*x = BlobMeta{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[9]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *BlobMeta) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*BlobMeta) ProtoMessage() {}

func (x *BlobMeta) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[9]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use BlobMeta.ProtoReflect.Descriptor instead.
func (*BlobMeta) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{9}
}

func (x *BlobMeta) GetBlob() *Blob {
	if x != nil {
		return x.Blob
	}
	return nil
}

func (x *BlobMeta) GetOffset() int64 {
	if x != nil {
		return x.Offset
	}
	return 0
}

func (x *BlobMeta) GetState() BlobState {
	if x != nil {
		return x.State
	}
	return BlobState_COMMITTED
}

func (x *BlobMeta) GetDtime() *timestamppb.Timestamp {
	if x != nil {
		return x.Dtime
	}
	return nil
}

//
// Response from  GetPartitionMetadataRequest
// containing all matched blob int the DB.
type GetPartitionMetadataResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Offset int64       `protobuf:"varint,1,opt,name=offset,proto3" json:"offset,omitempty"`
	Blobs  []*BlobMeta `protobuf:"bytes,2,rep,name=blobs,proto3" json:"blobs,omitempty"`
}

func (x *GetPartitionMetadataResponse) Reset() {
	*x = GetPartitionMetadataResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_rpc_proto_msgTypes[10]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetPartitionMetadataResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetPartitionMetadataResponse) ProtoMessage() {}

func (x *GetPartitionMetadataResponse) ProtoReflect() protoreflect.Message {
	mi := &file_rpc_proto_msgTypes[10]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetPartitionMetadataResponse.ProtoReflect.Descriptor instead.
func (*GetPartitionMetadataResponse) Descriptor() ([]byte, []int) {
	return file_rpc_proto_rawDescGZIP(), []int{10}
}

func (x *GetPartitionMetadataResponse) GetOffset() int64 {
	if x != nil {
		return x.Offset
	}
	return 0
}

func (x *GetPartitionMetadataResponse) GetBlobs() []*BlobMeta {
	if x != nil {
		return x.Blobs
	}
	return nil
}

var File_rpc_proto protoreflect.FileDescriptor

var file_rpc_proto_rawDesc = []byte{
	0x0a, 0x09, 0x72, 0x70, 0x63, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x0c, 0x65, 0x78, 0x6f,
	0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x1a, 0x1f, 0x67, 0x6f, 0x6f, 0x67, 0x6c,
	0x65, 0x2f, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66, 0x2f, 0x74, 0x69, 0x6d, 0x65, 0x73,
	0x74, 0x61, 0x6d, 0x70, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x22, 0x51, 0x0a, 0x04, 0x42, 0x6c,
	0x6f, 0x62, 0x12, 0x1c, 0x0a, 0x09, 0x70, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x18,
	0x01, 0x20, 0x01, 0x28, 0x05, 0x52, 0x09, 0x70, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e,
	0x12, 0x17, 0x0a, 0x07, 0x62, 0x6c, 0x6f, 0x62, 0x5f, 0x69, 0x64, 0x18, 0x02, 0x20, 0x01, 0x28,
	0x10, 0x52, 0x06, 0x62, 0x6c, 0x6f, 0x62, 0x49, 0x64, 0x12, 0x12, 0x0a, 0x04, 0x73, 0x69, 0x7a,
	0x65, 0x18, 0x03, 0x20, 0x01, 0x28, 0x05, 0x52, 0x04, 0x73, 0x69, 0x7a, 0x65, 0x22, 0x65, 0x0a,
	0x0b, 0x50, 0x61, 0x72, 0x74, 0x69, 0x61, 0x6c, 0x42, 0x6c, 0x6f, 0x62, 0x12, 0x26, 0x0a, 0x04,
	0x62, 0x6c, 0x6f, 0x62, 0x18, 0x01, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x12, 0x2e, 0x65, 0x78, 0x6f,
	0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x42, 0x6c, 0x6f, 0x62, 0x52, 0x04,
	0x62, 0x6c, 0x6f, 0x62, 0x12, 0x16, 0x0a, 0x06, 0x6f, 0x66, 0x66, 0x73, 0x65, 0x74, 0x18, 0x02,
	0x20, 0x01, 0x28, 0x05, 0x52, 0x06, 0x6f, 0x66, 0x66, 0x73, 0x65, 0x74, 0x12, 0x16, 0x0a, 0x06,
	0x6c, 0x65, 0x6e, 0x67, 0x74, 0x68, 0x18, 0x03, 0x20, 0x01, 0x28, 0x05, 0x52, 0x06, 0x6c, 0x65,
	0x6e, 0x67, 0x74, 0x68, 0x22, 0x64, 0x0a, 0x08, 0x43, 0x68, 0x65, 0x63, 0x6b, 0x73, 0x75, 0x6d,
	0x12, 0x2c, 0x0a, 0x04, 0x74, 0x79, 0x70, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x0e, 0x32, 0x18,
	0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x44, 0x69,
	0x67, 0x65, 0x73, 0x74, 0x54, 0x79, 0x70, 0x65, 0x52, 0x04, 0x74, 0x79, 0x70, 0x65, 0x12, 0x12,
	0x0a, 0x04, 0x73, 0x69, 0x7a, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x05, 0x52, 0x04, 0x73, 0x69,
	0x7a, 0x65, 0x12, 0x16, 0x0a, 0x06, 0x64, 0x69, 0x67, 0x65, 0x73, 0x74, 0x18, 0x03, 0x20, 0x01,
	0x28, 0x03, 0x52, 0x06, 0x64, 0x69, 0x67, 0x65, 0x73, 0x74, 0x22, 0x52, 0x0a, 0x08, 0x47, 0x65,
	0x74, 0x43, 0x68, 0x75, 0x6e, 0x6b, 0x12, 0x12, 0x0a, 0x04, 0x64, 0x61, 0x74, 0x61, 0x18, 0x01,
	0x20, 0x01, 0x28, 0x0c, 0x52, 0x04, 0x64, 0x61, 0x74, 0x61, 0x12, 0x32, 0x0a, 0x08, 0x63, 0x68,
	0x65, 0x63, 0x6b, 0x73, 0x75, 0x6d, 0x18, 0x02, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x16, 0x2e, 0x65,
	0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x43, 0x68, 0x65, 0x63,
	0x6b, 0x73, 0x75, 0x6d, 0x52, 0x08, 0x63, 0x68, 0x65, 0x63, 0x6b, 0x73, 0x75, 0x6d, 0x22, 0x25,
	0x0a, 0x0f, 0x47, 0x65, 0x74, 0x50, 0x61, 0x72, 0x74, 0x69, 0x61, 0x6c, 0x43, 0x68, 0x75, 0x6e,
	0x6b, 0x12, 0x12, 0x0a, 0x04, 0x64, 0x61, 0x74, 0x61, 0x18, 0x01, 0x20, 0x01, 0x28, 0x0c, 0x52,
	0x04, 0x64, 0x61, 0x74, 0x61, 0x22, 0x7a, 0x0a, 0x08, 0x50, 0x75, 0x74, 0x43, 0x68, 0x75, 0x6e,
	0x6b, 0x12, 0x12, 0x0a, 0x04, 0x64, 0x61, 0x74, 0x61, 0x18, 0x01, 0x20, 0x01, 0x28, 0x0c, 0x52,
	0x04, 0x64, 0x61, 0x74, 0x61, 0x12, 0x32, 0x0a, 0x08, 0x63, 0x68, 0x65, 0x63, 0x6b, 0x73, 0x75,
	0x6d, 0x18, 0x02, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x16, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61,
	0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x43, 0x68, 0x65, 0x63, 0x6b, 0x73, 0x75, 0x6d, 0x52,
	0x08, 0x63, 0x68, 0x65, 0x63, 0x6b, 0x73, 0x75, 0x6d, 0x12, 0x26, 0x0a, 0x04, 0x62, 0x6c, 0x6f,
	0x62, 0x18, 0x03, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x12, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61,
	0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x42, 0x6c, 0x6f, 0x62, 0x52, 0x04, 0x62, 0x6c, 0x6f,
	0x62, 0x22, 0x0b, 0x0a, 0x09, 0x50, 0x75, 0x74, 0x52, 0x65, 0x73, 0x75, 0x6c, 0x74, 0x22, 0x0e,
	0x0a, 0x0c, 0x44, 0x65, 0x6c, 0x65, 0x74, 0x65, 0x52, 0x65, 0x73, 0x75, 0x6c, 0x74, 0x22, 0x53,
	0x0a, 0x1b, 0x47, 0x65, 0x74, 0x50, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x4d, 0x65,
	0x74, 0x61, 0x64, 0x61, 0x74, 0x61, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x1c, 0x0a,
	0x09, 0x70, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x18, 0x01, 0x20, 0x01, 0x28, 0x05,
	0x52, 0x09, 0x70, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x12, 0x16, 0x0a, 0x06, 0x6f,
	0x66, 0x66, 0x73, 0x65, 0x74, 0x18, 0x02, 0x20, 0x01, 0x28, 0x03, 0x52, 0x06, 0x6f, 0x66, 0x66,
	0x73, 0x65, 0x74, 0x22, 0xba, 0x01, 0x0a, 0x08, 0x42, 0x6c, 0x6f, 0x62, 0x4d, 0x65, 0x74, 0x61,
	0x12, 0x26, 0x0a, 0x04, 0x62, 0x6c, 0x6f, 0x62, 0x18, 0x01, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x12,
	0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x42, 0x6c,
	0x6f, 0x62, 0x52, 0x04, 0x62, 0x6c, 0x6f, 0x62, 0x12, 0x16, 0x0a, 0x06, 0x6f, 0x66, 0x66, 0x73,
	0x65, 0x74, 0x18, 0x02, 0x20, 0x01, 0x28, 0x03, 0x52, 0x06, 0x6f, 0x66, 0x66, 0x73, 0x65, 0x74,
	0x12, 0x2d, 0x0a, 0x05, 0x73, 0x74, 0x61, 0x74, 0x65, 0x18, 0x03, 0x20, 0x01, 0x28, 0x0e, 0x32,
	0x17, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x42,
	0x6c, 0x6f, 0x62, 0x53, 0x74, 0x61, 0x74, 0x65, 0x52, 0x05, 0x73, 0x74, 0x61, 0x74, 0x65, 0x12,
	0x35, 0x0a, 0x05, 0x64, 0x74, 0x69, 0x6d, 0x65, 0x18, 0x04, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x1a,
	0x2e, 0x67, 0x6f, 0x6f, 0x67, 0x6c, 0x65, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66,
	0x2e, 0x54, 0x69, 0x6d, 0x65, 0x73, 0x74, 0x61, 0x6d, 0x70, 0x48, 0x00, 0x52, 0x05, 0x64, 0x74,
	0x69, 0x6d, 0x65, 0x88, 0x01, 0x01, 0x42, 0x08, 0x0a, 0x06, 0x5f, 0x64, 0x74, 0x69, 0x6d, 0x65,
	0x22, 0x64, 0x0a, 0x1c, 0x47, 0x65, 0x74, 0x50, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e,
	0x4d, 0x65, 0x74, 0x61, 0x64, 0x61, 0x74, 0x61, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65,
	0x12, 0x16, 0x0a, 0x06, 0x6f, 0x66, 0x66, 0x73, 0x65, 0x74, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03,
	0x52, 0x06, 0x6f, 0x66, 0x66, 0x73, 0x65, 0x74, 0x12, 0x2c, 0x0a, 0x05, 0x62, 0x6c, 0x6f, 0x62,
	0x73, 0x18, 0x02, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x16, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61,
	0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x42, 0x6c, 0x6f, 0x62, 0x4d, 0x65, 0x74, 0x61, 0x52,
	0x05, 0x62, 0x6c, 0x6f, 0x62, 0x73, 0x2a, 0x20, 0x0a, 0x0a, 0x44, 0x69, 0x67, 0x65, 0x73, 0x74,
	0x54, 0x79, 0x70, 0x65, 0x12, 0x09, 0x0a, 0x05, 0x58, 0x48, 0x48, 0x36, 0x34, 0x10, 0x00, 0x12,
	0x07, 0x0a, 0x03, 0x4d, 0x44, 0x35, 0x10, 0x01, 0x2a, 0x27, 0x0a, 0x09, 0x42, 0x6c, 0x6f, 0x62,
	0x53, 0x74, 0x61, 0x74, 0x65, 0x12, 0x0d, 0x0a, 0x09, 0x43, 0x4f, 0x4d, 0x4d, 0x49, 0x54, 0x54,
	0x45, 0x44, 0x10, 0x00, 0x12, 0x0b, 0x0a, 0x07, 0x44, 0x45, 0x4c, 0x45, 0x54, 0x45, 0x44, 0x10,
	0x01, 0x32, 0xf7, 0x02, 0x0a, 0x09, 0x42, 0x6c, 0x6f, 0x62, 0x53, 0x74, 0x6f, 0x72, 0x65, 0x12,
	0x3a, 0x0a, 0x03, 0x50, 0x75, 0x74, 0x12, 0x16, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c,
	0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x50, 0x75, 0x74, 0x43, 0x68, 0x75, 0x6e, 0x6b, 0x1a, 0x17,
	0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x50, 0x75,
	0x74, 0x52, 0x65, 0x73, 0x75, 0x6c, 0x74, 0x22, 0x00, 0x28, 0x01, 0x12, 0x4a, 0x0a, 0x0a, 0x47,
	0x65, 0x74, 0x50, 0x61, 0x72, 0x74, 0x69, 0x61, 0x6c, 0x12, 0x19, 0x2e, 0x65, 0x78, 0x6f, 0x73,
	0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x50, 0x61, 0x72, 0x74, 0x69, 0x61, 0x6c,
	0x42, 0x6c, 0x6f, 0x62, 0x1a, 0x1d, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e,
	0x73, 0x6f, 0x73, 0x2e, 0x47, 0x65, 0x74, 0x50, 0x61, 0x72, 0x74, 0x69, 0x61, 0x6c, 0x43, 0x68,
	0x75, 0x6e, 0x6b, 0x22, 0x00, 0x30, 0x01, 0x12, 0x35, 0x0a, 0x03, 0x47, 0x65, 0x74, 0x12, 0x12,
	0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x42, 0x6c,
	0x6f, 0x62, 0x1a, 0x16, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f,
	0x73, 0x2e, 0x47, 0x65, 0x74, 0x43, 0x68, 0x75, 0x6e, 0x6b, 0x22, 0x00, 0x30, 0x01, 0x12, 0x3a,
	0x0a, 0x06, 0x44, 0x65, 0x6c, 0x65, 0x74, 0x65, 0x12, 0x12, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63,
	0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x42, 0x6c, 0x6f, 0x62, 0x1a, 0x1a, 0x2e, 0x65,
	0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x44, 0x65, 0x6c, 0x65,
	0x74, 0x65, 0x52, 0x65, 0x73, 0x75, 0x6c, 0x74, 0x22, 0x00, 0x12, 0x6f, 0x0a, 0x14, 0x47, 0x65,
	0x74, 0x50, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x4d, 0x65, 0x74, 0x61, 0x64, 0x61,
	0x74, 0x61, 0x12, 0x29, 0x2e, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f,
	0x73, 0x2e, 0x47, 0x65, 0x74, 0x50, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x4d, 0x65,
	0x74, 0x61, 0x64, 0x61, 0x74, 0x61, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x2a, 0x2e,
	0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2e, 0x73, 0x6f, 0x73, 0x2e, 0x47, 0x65, 0x74,
	0x50, 0x61, 0x72, 0x74, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x4d, 0x65, 0x74, 0x61, 0x64, 0x61, 0x74,
	0x61, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x22, 0x00, 0x42, 0x2a, 0x42, 0x09, 0x42,
	0x6c, 0x6f, 0x62, 0x50, 0x72, 0x6f, 0x74, 0x6f, 0x5a, 0x1d, 0x67, 0x69, 0x74, 0x68, 0x75, 0x62,
	0x2e, 0x63, 0x6f, 0x6d, 0x2f, 0x65, 0x78, 0x6f, 0x73, 0x63, 0x61, 0x6c, 0x65, 0x2f, 0x73, 0x6f,
	0x73, 0x2f, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x06, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x33,
}

var (
	file_rpc_proto_rawDescOnce sync.Once
	file_rpc_proto_rawDescData = file_rpc_proto_rawDesc
)

func file_rpc_proto_rawDescGZIP() []byte {
	file_rpc_proto_rawDescOnce.Do(func() {
		file_rpc_proto_rawDescData = protoimpl.X.CompressGZIP(file_rpc_proto_rawDescData)
	})
	return file_rpc_proto_rawDescData
}

var file_rpc_proto_enumTypes = make([]protoimpl.EnumInfo, 2)
var file_rpc_proto_msgTypes = make([]protoimpl.MessageInfo, 11)
var file_rpc_proto_goTypes = []interface{}{
	(DigestType)(0),                      // 0: exoscale.sos.DigestType
	(BlobState)(0),                       // 1: exoscale.sos.BlobState
	(*Blob)(nil),                         // 2: exoscale.sos.Blob
	(*PartialBlob)(nil),                  // 3: exoscale.sos.PartialBlob
	(*Checksum)(nil),                     // 4: exoscale.sos.Checksum
	(*GetChunk)(nil),                     // 5: exoscale.sos.GetChunk
	(*GetPartialChunk)(nil),              // 6: exoscale.sos.GetPartialChunk
	(*PutChunk)(nil),                     // 7: exoscale.sos.PutChunk
	(*PutResult)(nil),                    // 8: exoscale.sos.PutResult
	(*DeleteResult)(nil),                 // 9: exoscale.sos.DeleteResult
	(*GetPartitionMetadataRequest)(nil),  // 10: exoscale.sos.GetPartitionMetadataRequest
	(*BlobMeta)(nil),                     // 11: exoscale.sos.BlobMeta
	(*GetPartitionMetadataResponse)(nil), // 12: exoscale.sos.GetPartitionMetadataResponse
	(*timestamppb.Timestamp)(nil),        // 13: google.protobuf.Timestamp
}
var file_rpc_proto_depIdxs = []int32{
	2,  // 0: exoscale.sos.PartialBlob.blob:type_name -> exoscale.sos.Blob
	0,  // 1: exoscale.sos.Checksum.type:type_name -> exoscale.sos.DigestType
	4,  // 2: exoscale.sos.GetChunk.checksum:type_name -> exoscale.sos.Checksum
	4,  // 3: exoscale.sos.PutChunk.checksum:type_name -> exoscale.sos.Checksum
	2,  // 4: exoscale.sos.PutChunk.blob:type_name -> exoscale.sos.Blob
	2,  // 5: exoscale.sos.BlobMeta.blob:type_name -> exoscale.sos.Blob
	1,  // 6: exoscale.sos.BlobMeta.state:type_name -> exoscale.sos.BlobState
	13, // 7: exoscale.sos.BlobMeta.dtime:type_name -> google.protobuf.Timestamp
	11, // 8: exoscale.sos.GetPartitionMetadataResponse.blobs:type_name -> exoscale.sos.BlobMeta
	7,  // 9: exoscale.sos.BlobStore.Put:input_type -> exoscale.sos.PutChunk
	3,  // 10: exoscale.sos.BlobStore.GetPartial:input_type -> exoscale.sos.PartialBlob
	2,  // 11: exoscale.sos.BlobStore.Get:input_type -> exoscale.sos.Blob
	2,  // 12: exoscale.sos.BlobStore.Delete:input_type -> exoscale.sos.Blob
	10, // 13: exoscale.sos.BlobStore.GetPartitionMetadata:input_type -> exoscale.sos.GetPartitionMetadataRequest
	8,  // 14: exoscale.sos.BlobStore.Put:output_type -> exoscale.sos.PutResult
	6,  // 15: exoscale.sos.BlobStore.GetPartial:output_type -> exoscale.sos.GetPartialChunk
	5,  // 16: exoscale.sos.BlobStore.Get:output_type -> exoscale.sos.GetChunk
	9,  // 17: exoscale.sos.BlobStore.Delete:output_type -> exoscale.sos.DeleteResult
	12, // 18: exoscale.sos.BlobStore.GetPartitionMetadata:output_type -> exoscale.sos.GetPartitionMetadataResponse
	14, // [14:19] is the sub-list for method output_type
	9,  // [9:14] is the sub-list for method input_type
	9,  // [9:9] is the sub-list for extension type_name
	9,  // [9:9] is the sub-list for extension extendee
	0,  // [0:9] is the sub-list for field type_name
}

func init() { file_rpc_proto_init() }
func file_rpc_proto_init() {
	if File_rpc_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_rpc_proto_msgTypes[0].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*Blob); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[1].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*PartialBlob); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[2].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*Checksum); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[3].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetChunk); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[4].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetPartialChunk); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[5].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*PutChunk); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[6].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*PutResult); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[7].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*DeleteResult); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[8].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetPartitionMetadataRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[9].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*BlobMeta); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_rpc_proto_msgTypes[10].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetPartitionMetadataResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	file_rpc_proto_msgTypes[9].OneofWrappers = []interface{}{}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_rpc_proto_rawDesc,
			NumEnums:      2,
			NumMessages:   11,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_rpc_proto_goTypes,
		DependencyIndexes: file_rpc_proto_depIdxs,
		EnumInfos:         file_rpc_proto_enumTypes,
		MessageInfos:      file_rpc_proto_msgTypes,
	}.Build()
	File_rpc_proto = out.File
	file_rpc_proto_rawDesc = nil
	file_rpc_proto_goTypes = nil
	file_rpc_proto_depIdxs = nil
}
