package exoscale.sos;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The service definition
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: rpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BlobStoreGrpc {

  private BlobStoreGrpc() {}

  public static final String SERVICE_NAME = "exoscale.sos.BlobStore";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<exoscale.sos.BlobProto.PutChunk,
      exoscale.sos.BlobProto.PutResult> getPutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Put",
      requestType = exoscale.sos.BlobProto.PutChunk.class,
      responseType = exoscale.sos.BlobProto.PutResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<exoscale.sos.BlobProto.PutChunk,
      exoscale.sos.BlobProto.PutResult> getPutMethod() {
    io.grpc.MethodDescriptor<exoscale.sos.BlobProto.PutChunk, exoscale.sos.BlobProto.PutResult> getPutMethod;
    if ((getPutMethod = BlobStoreGrpc.getPutMethod) == null) {
      synchronized (BlobStoreGrpc.class) {
        if ((getPutMethod = BlobStoreGrpc.getPutMethod) == null) {
          BlobStoreGrpc.getPutMethod = getPutMethod =
              io.grpc.MethodDescriptor.<exoscale.sos.BlobProto.PutChunk, exoscale.sos.BlobProto.PutResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Put"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.PutChunk.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.PutResult.getDefaultInstance()))
              .setSchemaDescriptor(new BlobStoreMethodDescriptorSupplier("Put"))
              .build();
        }
      }
    }
    return getPutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<exoscale.sos.BlobProto.PartialBlob,
      exoscale.sos.BlobProto.GetPartialChunk> getGetPartialMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPartial",
      requestType = exoscale.sos.BlobProto.PartialBlob.class,
      responseType = exoscale.sos.BlobProto.GetPartialChunk.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<exoscale.sos.BlobProto.PartialBlob,
      exoscale.sos.BlobProto.GetPartialChunk> getGetPartialMethod() {
    io.grpc.MethodDescriptor<exoscale.sos.BlobProto.PartialBlob, exoscale.sos.BlobProto.GetPartialChunk> getGetPartialMethod;
    if ((getGetPartialMethod = BlobStoreGrpc.getGetPartialMethod) == null) {
      synchronized (BlobStoreGrpc.class) {
        if ((getGetPartialMethod = BlobStoreGrpc.getGetPartialMethod) == null) {
          BlobStoreGrpc.getGetPartialMethod = getGetPartialMethod =
              io.grpc.MethodDescriptor.<exoscale.sos.BlobProto.PartialBlob, exoscale.sos.BlobProto.GetPartialChunk>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPartial"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.PartialBlob.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.GetPartialChunk.getDefaultInstance()))
              .setSchemaDescriptor(new BlobStoreMethodDescriptorSupplier("GetPartial"))
              .build();
        }
      }
    }
    return getGetPartialMethod;
  }

  private static volatile io.grpc.MethodDescriptor<exoscale.sos.BlobProto.Blob,
      exoscale.sos.BlobProto.GetChunk> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType = exoscale.sos.BlobProto.Blob.class,
      responseType = exoscale.sos.BlobProto.GetChunk.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<exoscale.sos.BlobProto.Blob,
      exoscale.sos.BlobProto.GetChunk> getGetMethod() {
    io.grpc.MethodDescriptor<exoscale.sos.BlobProto.Blob, exoscale.sos.BlobProto.GetChunk> getGetMethod;
    if ((getGetMethod = BlobStoreGrpc.getGetMethod) == null) {
      synchronized (BlobStoreGrpc.class) {
        if ((getGetMethod = BlobStoreGrpc.getGetMethod) == null) {
          BlobStoreGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<exoscale.sos.BlobProto.Blob, exoscale.sos.BlobProto.GetChunk>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.Blob.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.GetChunk.getDefaultInstance()))
              .setSchemaDescriptor(new BlobStoreMethodDescriptorSupplier("Get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<exoscale.sos.BlobProto.Blob,
      exoscale.sos.BlobProto.DeleteResult> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = exoscale.sos.BlobProto.Blob.class,
      responseType = exoscale.sos.BlobProto.DeleteResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<exoscale.sos.BlobProto.Blob,
      exoscale.sos.BlobProto.DeleteResult> getDeleteMethod() {
    io.grpc.MethodDescriptor<exoscale.sos.BlobProto.Blob, exoscale.sos.BlobProto.DeleteResult> getDeleteMethod;
    if ((getDeleteMethod = BlobStoreGrpc.getDeleteMethod) == null) {
      synchronized (BlobStoreGrpc.class) {
        if ((getDeleteMethod = BlobStoreGrpc.getDeleteMethod) == null) {
          BlobStoreGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<exoscale.sos.BlobProto.Blob, exoscale.sos.BlobProto.DeleteResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.Blob.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.DeleteResult.getDefaultInstance()))
              .setSchemaDescriptor(new BlobStoreMethodDescriptorSupplier("Delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<exoscale.sos.BlobProto.GetPartitionMetadataRequest,
      exoscale.sos.BlobProto.GetPartitionMetadataResponse> getGetPartitionMetadataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPartitionMetadata",
      requestType = exoscale.sos.BlobProto.GetPartitionMetadataRequest.class,
      responseType = exoscale.sos.BlobProto.GetPartitionMetadataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<exoscale.sos.BlobProto.GetPartitionMetadataRequest,
      exoscale.sos.BlobProto.GetPartitionMetadataResponse> getGetPartitionMetadataMethod() {
    io.grpc.MethodDescriptor<exoscale.sos.BlobProto.GetPartitionMetadataRequest, exoscale.sos.BlobProto.GetPartitionMetadataResponse> getGetPartitionMetadataMethod;
    if ((getGetPartitionMetadataMethod = BlobStoreGrpc.getGetPartitionMetadataMethod) == null) {
      synchronized (BlobStoreGrpc.class) {
        if ((getGetPartitionMetadataMethod = BlobStoreGrpc.getGetPartitionMetadataMethod) == null) {
          BlobStoreGrpc.getGetPartitionMetadataMethod = getGetPartitionMetadataMethod =
              io.grpc.MethodDescriptor.<exoscale.sos.BlobProto.GetPartitionMetadataRequest, exoscale.sos.BlobProto.GetPartitionMetadataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPartitionMetadata"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.GetPartitionMetadataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exoscale.sos.BlobProto.GetPartitionMetadataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BlobStoreMethodDescriptorSupplier("GetPartitionMetadata"))
              .build();
        }
      }
    }
    return getGetPartitionMetadataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BlobStoreStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlobStoreStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlobStoreStub>() {
        @java.lang.Override
        public BlobStoreStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlobStoreStub(channel, callOptions);
        }
      };
    return BlobStoreStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BlobStoreBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlobStoreBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlobStoreBlockingStub>() {
        @java.lang.Override
        public BlobStoreBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlobStoreBlockingStub(channel, callOptions);
        }
      };
    return BlobStoreBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BlobStoreFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlobStoreFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlobStoreFutureStub>() {
        @java.lang.Override
        public BlobStoreFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlobStoreFutureStub(channel, callOptions);
        }
      };
    return BlobStoreFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The service definition
   * </pre>
   */
  public static abstract class BlobStoreImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.PutChunk> put(
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.PutResult> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getPutMethod(), responseObserver);
    }

    /**
     */
    public void getPartial(exoscale.sos.BlobProto.PartialBlob request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetPartialChunk> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPartialMethod(), responseObserver);
    }

    /**
     */
    public void get(exoscale.sos.BlobProto.Blob request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetChunk> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     */
    public void delete(exoscale.sos.BlobProto.Blob request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.DeleteResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    public void getPartitionMetadata(exoscale.sos.BlobProto.GetPartitionMetadataRequest request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetPartitionMetadataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPartitionMetadataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPutMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                exoscale.sos.BlobProto.PutChunk,
                exoscale.sos.BlobProto.PutResult>(
                  this, METHODID_PUT)))
          .addMethod(
            getGetPartialMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                exoscale.sos.BlobProto.PartialBlob,
                exoscale.sos.BlobProto.GetPartialChunk>(
                  this, METHODID_GET_PARTIAL)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                exoscale.sos.BlobProto.Blob,
                exoscale.sos.BlobProto.GetChunk>(
                  this, METHODID_GET)))
          .addMethod(
            getDeleteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                exoscale.sos.BlobProto.Blob,
                exoscale.sos.BlobProto.DeleteResult>(
                  this, METHODID_DELETE)))
          .addMethod(
            getGetPartitionMetadataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                exoscale.sos.BlobProto.GetPartitionMetadataRequest,
                exoscale.sos.BlobProto.GetPartitionMetadataResponse>(
                  this, METHODID_GET_PARTITION_METADATA)))
          .build();
    }
  }

  /**
   * <pre>
   * The service definition
   * </pre>
   */
  public static final class BlobStoreStub extends io.grpc.stub.AbstractAsyncStub<BlobStoreStub> {
    private BlobStoreStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlobStoreStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlobStoreStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.PutChunk> put(
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.PutResult> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void getPartial(exoscale.sos.BlobProto.PartialBlob request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetPartialChunk> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetPartialMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void get(exoscale.sos.BlobProto.Blob request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetChunk> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(exoscale.sos.BlobProto.Blob request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.DeleteResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPartitionMetadata(exoscale.sos.BlobProto.GetPartitionMetadataRequest request,
        io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetPartitionMetadataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPartitionMetadataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The service definition
   * </pre>
   */
  public static final class BlobStoreBlockingStub extends io.grpc.stub.AbstractBlockingStub<BlobStoreBlockingStub> {
    private BlobStoreBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlobStoreBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlobStoreBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<exoscale.sos.BlobProto.GetPartialChunk> getPartial(
        exoscale.sos.BlobProto.PartialBlob request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetPartialMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<exoscale.sos.BlobProto.GetChunk> get(
        exoscale.sos.BlobProto.Blob request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     */
    public exoscale.sos.BlobProto.DeleteResult delete(exoscale.sos.BlobProto.Blob request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public exoscale.sos.BlobProto.GetPartitionMetadataResponse getPartitionMetadata(exoscale.sos.BlobProto.GetPartitionMetadataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPartitionMetadataMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The service definition
   * </pre>
   */
  public static final class BlobStoreFutureStub extends io.grpc.stub.AbstractFutureStub<BlobStoreFutureStub> {
    private BlobStoreFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlobStoreFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlobStoreFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<exoscale.sos.BlobProto.DeleteResult> delete(
        exoscale.sos.BlobProto.Blob request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<exoscale.sos.BlobProto.GetPartitionMetadataResponse> getPartitionMetadata(
        exoscale.sos.BlobProto.GetPartitionMetadataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPartitionMetadataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PARTIAL = 0;
  private static final int METHODID_GET = 1;
  private static final int METHODID_DELETE = 2;
  private static final int METHODID_GET_PARTITION_METADATA = 3;
  private static final int METHODID_PUT = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BlobStoreImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BlobStoreImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PARTIAL:
          serviceImpl.getPartial((exoscale.sos.BlobProto.PartialBlob) request,
              (io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetPartialChunk>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((exoscale.sos.BlobProto.Blob) request,
              (io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetChunk>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((exoscale.sos.BlobProto.Blob) request,
              (io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.DeleteResult>) responseObserver);
          break;
        case METHODID_GET_PARTITION_METADATA:
          serviceImpl.getPartitionMetadata((exoscale.sos.BlobProto.GetPartitionMetadataRequest) request,
              (io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.GetPartitionMetadataResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.put(
              (io.grpc.stub.StreamObserver<exoscale.sos.BlobProto.PutResult>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BlobStoreBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BlobStoreBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return exoscale.sos.BlobProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BlobStore");
    }
  }

  private static final class BlobStoreFileDescriptorSupplier
      extends BlobStoreBaseDescriptorSupplier {
    BlobStoreFileDescriptorSupplier() {}
  }

  private static final class BlobStoreMethodDescriptorSupplier
      extends BlobStoreBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BlobStoreMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BlobStoreGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BlobStoreFileDescriptorSupplier())
              .addMethod(getPutMethod())
              .addMethod(getGetPartialMethod())
              .addMethod(getGetMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getGetPartitionMetadataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
