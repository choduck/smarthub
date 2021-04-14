package com.wooribank.smarthub.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: Grpc.proto")
public final class GrpcServiceGrpc {

  private GrpcServiceGrpc() {}

  public static final String SERVICE_NAME = "com.wooribank.smarthub.grpc.GrpcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.wooribank.smarthub.grpc.Grpc.GrpcMessage,
      com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer> getGrpcMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "grpc",
      requestType = com.wooribank.smarthub.grpc.Grpc.GrpcMessage.class,
      responseType = com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.wooribank.smarthub.grpc.Grpc.GrpcMessage,
      com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer> getGrpcMethod() {
    io.grpc.MethodDescriptor<com.wooribank.smarthub.grpc.Grpc.GrpcMessage, com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer> getGrpcMethod;
    if ((getGrpcMethod = GrpcServiceGrpc.getGrpcMethod) == null) {
      synchronized (GrpcServiceGrpc.class) {
        if ((getGrpcMethod = GrpcServiceGrpc.getGrpcMethod) == null) {
          GrpcServiceGrpc.getGrpcMethod = getGrpcMethod = 
              io.grpc.MethodDescriptor.<com.wooribank.smarthub.grpc.Grpc.GrpcMessage, com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "com.wooribank.smarthub.grpc.GrpcService", "grpc"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wooribank.smarthub.grpc.Grpc.GrpcMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer.getDefaultInstance()))
                  .setSchemaDescriptor(new GrpcServiceMethodDescriptorSupplier("grpc"))
                  .build();
          }
        }
     }
     return getGrpcMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wooribank.smarthub.grpc.Grpc.ProcessRequest,
      com.wooribank.smarthub.grpc.Grpc.ProcessResponse> getProcessMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "process",
      requestType = com.wooribank.smarthub.grpc.Grpc.ProcessRequest.class,
      responseType = com.wooribank.smarthub.grpc.Grpc.ProcessResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.wooribank.smarthub.grpc.Grpc.ProcessRequest,
      com.wooribank.smarthub.grpc.Grpc.ProcessResponse> getProcessMethod() {
    io.grpc.MethodDescriptor<com.wooribank.smarthub.grpc.Grpc.ProcessRequest, com.wooribank.smarthub.grpc.Grpc.ProcessResponse> getProcessMethod;
    if ((getProcessMethod = GrpcServiceGrpc.getProcessMethod) == null) {
      synchronized (GrpcServiceGrpc.class) {
        if ((getProcessMethod = GrpcServiceGrpc.getProcessMethod) == null) {
          GrpcServiceGrpc.getProcessMethod = getProcessMethod = 
              io.grpc.MethodDescriptor.<com.wooribank.smarthub.grpc.Grpc.ProcessRequest, com.wooribank.smarthub.grpc.Grpc.ProcessResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "com.wooribank.smarthub.grpc.GrpcService", "process"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wooribank.smarthub.grpc.Grpc.ProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wooribank.smarthub.grpc.Grpc.ProcessResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new GrpcServiceMethodDescriptorSupplier("process"))
                  .build();
          }
        }
     }
     return getProcessMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GrpcServiceStub newStub(io.grpc.Channel channel) {
    return new GrpcServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GrpcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GrpcServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GrpcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GrpcServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class GrpcServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.GrpcMessage> grpc(
        io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer> responseObserver) {
      return asyncUnimplementedStreamingCall(getGrpcMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.ProcessRequest> process(
        io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.ProcessResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getProcessMethod(), responseObserver);
    }

    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGrpcMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.wooribank.smarthub.grpc.Grpc.GrpcMessage,
                com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer>(
                  this, METHODID_GRPC)))
          .addMethod(
            getProcessMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.wooribank.smarthub.grpc.Grpc.ProcessRequest,
                com.wooribank.smarthub.grpc.Grpc.ProcessResponse>(
                  this, METHODID_PROCESS)))
          .build();
    }
  }

  /**
   */
  public static final class GrpcServiceStub extends io.grpc.stub.AbstractStub<GrpcServiceStub> {
    private GrpcServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.GrpcMessage> grpc(
        io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getGrpcMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.ProcessRequest> process(
        io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.ProcessResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getProcessMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class GrpcServiceBlockingStub extends io.grpc.stub.AbstractStub<GrpcServiceBlockingStub> {
    private GrpcServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class GrpcServiceFutureStub extends io.grpc.stub.AbstractStub<GrpcServiceFutureStub> {
    private GrpcServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GrpcServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GrpcServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GRPC = 0;
  private static final int METHODID_PROCESS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GrpcServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GrpcServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }


    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }


    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GRPC:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.grpc(
              (io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer>) responseObserver);
        case METHODID_PROCESS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.process(
              (io.grpc.stub.StreamObserver<com.wooribank.smarthub.grpc.Grpc.ProcessResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GrpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GrpcServiceBaseDescriptorSupplier() {}


    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.wooribank.smarthub.grpc.Grpc.getDescriptor();
    }


    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GrpcService");
    }
  }

  private static final class GrpcServiceFileDescriptorSupplier
      extends GrpcServiceBaseDescriptorSupplier {
    GrpcServiceFileDescriptorSupplier() {}
  }

  private static final class GrpcServiceMethodDescriptorSupplier
      extends GrpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GrpcServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }


    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GrpcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GrpcServiceFileDescriptorSupplier())
              .addMethod(getGrpcMethod())
              .addMethod(getProcessMethod())
              .build();
        }
      }
    }
    return result;
  }
}
