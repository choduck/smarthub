import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.json.simple.JSONObject;

import com.wooribank.smarthub.grpc.Grpc.ProcessRequest;
import com.wooribank.smarthub.grpc.Grpc.ProcessResponse;
import com.wooribank.smarthub.grpc.GrpcServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcClientHub1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		clientSample();
	}

	public static void clientSample() {
	    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 20300).usePlaintext(true).build();
	    GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);

	    CountDownLatch latch = new CountDownLatch(10);
	    
		StreamObserver<ProcessResponse> replyStream = new StreamObserver<ProcessResponse>() {
	
		    public void onNext(ProcessResponse reply) {
		        
		    	System.out.println(reply);
		        
		       
		    }
		 

		    public void onError(Throwable t) {
		        t.printStackTrace();
		       // latch.countDown();
		    }
		 

		    public void onCompleted() {
		        System.out.println("We're done!");
		       // latch.countDown();
		    }
		};
		
		
        JSONObject hubInfo = new JSONObject();
        
        //정보 입력
        //영업점ID
        hubInfo.put("branch_id", "10101010");
        //스마트허브IP
        hubInfo.put("hub_ip", "192.168.000.016");
        //요청 테스크 설정
        hubInfo.put("send_time", new Date().toString());
        hubInfo.put("send_event", "1");
        hubInfo.put("issue_source", "M");
        hubInfo.put("issue_number", "1");
        hubInfo.put("issue_num_state", "1");
        hubInfo.put("session_id", "1123455");
        
        String hubInfoStr = hubInfo.toJSONString();
		
		// Send the request stream.
		StreamObserver<ProcessRequest> requestStream = grpcService.process(replyStream);
		ProcessRequest request = ProcessRequest.newBuilder().setRequestType(1).setArgs(hubInfoStr).build();
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
