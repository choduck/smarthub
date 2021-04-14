import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.json.simple.JSONObject;

import com.wooribank.smarthub.grpc.Grpc.ProcessRequest;
import com.wooribank.smarthub.grpc.Grpc.ProcessResponse;
import com.wooribank.smarthub.grpc.GrpcServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcClientHub33 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		clientSample();
	}

	public static void clientSample() {
	    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext(true).build();
	    GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);

	    CountDownLatch latch = new CountDownLatch(10);
	    
		StreamObserver<ProcessResponse> replyStream = new StreamObserver<ProcessResponse>() {
		    @Override
		    public void onNext(ProcessResponse reply) {
		        
		    	System.out.println(reply);
		        
		       
		    }
		 
		    @Override
		    public void onError(Throwable t) {
		        t.printStackTrace();
		        //latch.countDown();
		    }
		 
		    @Override
		    public void onCompleted() {
		        System.out.println("We're done!");
		        //latch.countDown();
		    }
		};
		
		
        JSONObject hubInfo = new JSONObject();
        
        //정보 입력
        //영업점ID
        hubInfo.put("branch_id", "10101012");
        //스마트허브IP
        hubInfo.put("hub_ip", "192.168.000.016");
        //요청 테스크 설정
        hubInfo.put("send_time", new Date().toString());
        hubInfo.put("send_event", "1");
        hubInfo.put("issue_source", "M");
        hubInfo.put("issue_number", "1");
        hubInfo.put("issue_num_state", "1");
        hubInfo.put("session_id", "f89b579c-326e-4a14-a95a-dab7789b6893");
        
        String hubInfoStr = hubInfo.toJSONString();
		
		// Send the request stream.
		StreamObserver<ProcessRequest> requestStream = grpcService.process(replyStream);
		ProcessRequest request = ProcessRequest.newBuilder().setRequestType(3).setArgs(hubInfoStr).build();
		requestStream.onNext(request);
		//requestStream.onCompleted();

		
		try {
			latch.await();
			//latch.await(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
