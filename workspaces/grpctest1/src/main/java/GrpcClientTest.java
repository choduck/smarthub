import java.util.concurrent.CountDownLatch;

import com.wooribank.smarthub.grpc.GrpcServiceGrpc;
import com.wooribank.smarthub.grpc.Grpc.GrpcMessage;
import com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		clientSample();
	}

	public static void clientSample() {
	    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext(true).build();
	    GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);

	    CountDownLatch latch = new CountDownLatch(10);
	    
		StreamObserver<GrpcMessageFromServer> replyStream = new StreamObserver<GrpcMessageFromServer>() {
		    @Override
		    public void onNext(GrpcMessageFromServer reply) {
		        System.out.println(reply);
		    	//assert reply.getMessage().equals("Hello, Armerian World!");
		    }
		 
		    @Override
		    public void onError(Throwable t) {
		        t.printStackTrace();
		        latch.countDown();
		    }
		 
		    @Override
		    public void onCompleted() {
		        System.out.println("We're done!");
		        latch.countDown();
		    }
		};
		 
		// Send the request stream.
		StreamObserver<GrpcMessage> requestStream = grpcService.grpc(replyStream);
		GrpcMessage request = GrpcMessage.newBuilder().setFrom("ArmerianWorld").setMessage("Hello").build();
		requestStream.onNext(request);
		//requestStream.onNext(request);
		//requestStream.onCompleted();
	
		try {
			latch.await();
			//latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
