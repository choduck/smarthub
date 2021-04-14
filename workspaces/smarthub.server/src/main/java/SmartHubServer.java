
import java.io.IOException;
import java.util.logging.Logger;

import com.wooribank.smarthub.grpc.ProcessRequest;
import com.wooribank.smarthub.grpc.ProcessResponse;
import com.wooribank.smarthub.grpc.SmartHubServerImpl;
import com.wooribank.smarthub.grpc.VerifiableCredentialGrpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class SmartHubServer {
  private static final Logger logger = Logger.getLogger(SmartHubServer.class.getName());

  private Server server;

  private void start() throws IOException {
    /* The port on which the server should run */
    int port = 50051;
    server = ServerBuilder.forPort(port)
        .addService(new SmartHubServerImpl())
        //.addService(new GreeterImpl())
        .build()
        .start();
    logger.info("Server started !!, listening on " + port);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        SmartHubServer.this.stop();
        System.err.println("*** server shut down");
      }
    });
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /**
   * Main launches the server from the command line.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final SmartHubServer server = new SmartHubServer();
    server.start();
    server.blockUntilShutdown();
  }

  static class GreeterImpl extends VerifiableCredentialGrpc.VerifiableCredentialImplBase {

	    
		@Override
		public void process(ProcessRequest request, StreamObserver<ProcessResponse> responseObserver) {

	    	ProcessResponse.Builder builder = ProcessResponse.newBuilder();
	    	builder.setStatus(-1);
			builder.setMessage("1234".toString());  
			ProcessResponse resp = builder.build();
			  
		    responseObserver.onNext(resp);
		    responseObserver.onCompleted();
//	   @Override
//	    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
//	      HelloReply reply = HelloReply.newBuilder().setMessage("Hello(((())))" + req.getName()).build();
//	      responseObserver.onNext(reply);
//	      responseObserver.onCompleted();
	    }
	  }


}
