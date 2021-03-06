// Code generated by protoc-gen-marshal-zap. DO NOT EDIT.
// source: schema.pb.go

package proto

import (
	"go.uber.org/zap/zapcore"
	"strconv"
)

// Reference imports to suppress errors if they are not otherwise used.
var _ = zapcore.NewNopCore
var _ = strconv.FormatInt

func (x *RefCountedBlob) MarshalLogObject(enc zapcore.ObjectEncoder) error {
	if x == nil {
		return nil
	}

	if obj, ok := interface{}(x.Blob).(zapcore.ObjectMarshaler); ok {
		enc.AddObject("blob", obj)
	} else {
		enc.AddReflected("blob", x.Blob)
	}

	enc.AddInt32("refcount", x.Refcount)

	return nil
}

func (x *BlobView) MarshalLogObject(enc zapcore.ObjectEncoder) error {
	if x == nil {
		return nil
	}

	if obj, ok := interface{}(x.PartialBlob).(zapcore.ObjectMarshaler); ok {
		enc.AddObject("partialBlob", obj)
	} else {
		enc.AddReflected("partialBlob", x.PartialBlob)
	}

	enc.AddInt64("extentOffset", x.ExtentOffset)

	return nil
}

func (x *Extent) MarshalLogObject(enc zapcore.ObjectEncoder) error {
	if x == nil {
		return nil
	}

	enc.AddString("uuid", x.Uuid)

	enc.AddInt64("diskOffset", x.DiskOffset)

	blobViewsArrMarshaller := func(enc zapcore.ArrayEncoder) error {
		for _, v := range x.BlobViews {

			if obj, ok := interface{}(v).(zapcore.ObjectMarshaler); ok {
				enc.AppendObject(obj)
			} else {
				enc.AppendReflected(v)
			}

		}
		return nil
	}
	enc.AddArray("blobViews", zapcore.ArrayMarshalerFunc(blobViewsArrMarshaller))

	enc.AddBool("isSnapshot", x.IsSnapshot)

	return nil
}

func (x *RecordTypeUnion) MarshalLogObject(enc zapcore.ObjectEncoder) error {
	if x == nil {
		return nil
	}

	if obj, ok := interface{}(x.XRefCountedBlob).(zapcore.ObjectMarshaler); ok {
		enc.AddObject("_RefCountedBlob", obj)
	} else {
		enc.AddReflected("_RefCountedBlob", x.XRefCountedBlob)
	}

	if obj, ok := interface{}(x.XExtent).(zapcore.ObjectMarshaler); ok {
		enc.AddObject("_Extent", obj)
	} else {
		enc.AddReflected("_Extent", x.XExtent)
	}

	return nil
}
