import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.wooribank.smarthub.grpc.Grpc.GrpcMessage;
import com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer;
import com.wooribank.smarthub.grpc.Grpc.ProcessRequest;
import com.wooribank.smarthub.grpc.Grpc.ProcessResponse;
import com.wooribank.smarthub.grpc.GrpcServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class McaSocketServer {
	// 연결할 포트를 지정합니다.
	private static final int PORT = 20301;
	// 스레드 풀의 최대 스레드 개수를 지정합니다.
	private static final int THREAD_CNT = 1000;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);
	public static void main(String[] args) {

		try {
			// 서버소켓 생성
			ServerSocket serverSocket = new ServerSocket(PORT);

			// 소켓서버가 종료될때까지 무한루프
			while(true){
				// 소켓 접속 요청이 올때까지 대기합니다.
				Socket socket = serverSocket.accept();
				try{
					// 요청이 오면 스레드 풀의 스레드로 소켓을 넣어줍니다.
					// 이후는 스레드 내에서 처리합니다.
					threadPool.execute(new ConnectionWrap(socket));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// 소켓 처리용 래퍼 클래스입니다.
class ConnectionWrap implements Runnable{

	private Socket socket = null;
	OutputStream stream = null;
	BufferedReader bufReader = null;
	
	public ConnectionWrap(Socket socket) {
		this.socket = socket;
	}

    static ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 20300).usePlaintext(true).build();
    static GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);

    String uniqueID = UUID.randomUUID().toString();
    
	public void run() {

	    JSONParser parser = new JSONParser();
	    JSONObject argObject = null;

		
		try {
			
			while(true) {
				// 응답을 위해 스트림을 얻어옵니다.
				stream = socket.getOutputStream();
				
				bufReader = new BufferedReader( new InputStreamReader( socket.getInputStream()));
			    
				String message = bufReader.readLine();
	
				System.out.println("message ==>" + message);
				
				try {
					argObject = (JSONObject) parser.parse(message);
				
					String branch_id = (String) argObject.get("branch_id");
					String hub_ip =  (String) argObject.get("hub_ip");
					String task_id =  (String) argObject.get("task_id");
					
					System.out.println("branch_id ==>" + branch_id);
					System.out.println("hub_ip ==>" + hub_ip);
					System.out.println("task_id ==>" + task_id);
					
					  
				    argObject.put("session_id", uniqueID);
				   
				    message = argObject.toString();

				    System.out.println("1.message ==>" + message);
				    
					branch_id = (String) argObject.get("branch_id");
					hub_ip =  (String) argObject.get("hub_ip");
					task_id =  (String) argObject.get("task_id");
					String session_id =  (String) argObject.get("session_id");
					
					System.out.println("branch_id ==>" + branch_id);
					System.out.println("hub_ip ==>" + hub_ip);
					System.out.println("task_id ==>" + task_id);
					System.out.println("session_id ==>" + session_id);
					
					clientGrpcService2(message,stream);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	
				
				//stream.write(new Date().toString().getBytes());
				
				//clientGrpcService2(message,stream);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("socket close!!");
				socket.close(); // 반드시 종료합니다.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void clientGrpcService(String inStr,final OutputStream stream) {
	    
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext(true).build();
	    GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);

	    final CountDownLatch latch = new CountDownLatch(10);
	    
		StreamObserver<GrpcMessageFromServer> replyStream = new StreamObserver<GrpcMessageFromServer>() {
		    
		    public void onNext(GrpcMessageFromServer reply) {
		        
		    	System.out.println(reply);
		        
		        try {
					stream.write(reply.toString().getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //assert reply.getMessage().equals("Hello, Armerian World!");
		    }
		 
		    
		    public void onError(Throwable t) {
		        t.printStackTrace();
		        latch.countDown();
		    }
		 
		    
		    public void onCompleted() {
		        System.out.println("We're done!");
		        latch.countDown();
		    }
		};
		 
		// Send the request stream.
		StreamObserver<GrpcMessage> requestStream = grpcService.grpc(replyStream);
		GrpcMessage request = GrpcMessage.newBuilder().setFrom("ArmerianWorld").setMessage(inStr).build();
		requestStream.onNext(request);
		//requestStream.onCompleted();
	
		try {
			//latch.await();
			latch.await(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clientGrpcService2(String inStr,final OutputStream stream) {

		
		StreamObserver<ProcessResponse> replyStream = new StreamObserver<ProcessResponse>() {
		    
		    public void onNext(ProcessResponse reply) {
		        
		    	System.out.println(reply);
		        
		        try {
					
		        	stream.write(reply.toString().getBytes());
				
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		    }
		 
		    
		    public void onError(Throwable t) {
		        t.printStackTrace();
		       
		    }
		 
		    
		    public void onCompleted() {
		        System.out.println("We're done!");
		       
		    }
		};
		 
		// Send the request stream.
		StreamObserver<ProcessRequest> requestStream = grpcService.process(replyStream);
		ProcessRequest request = ProcessRequest.newBuilder().setRequestType(4).setArgs(inStr).build();
		requestStream.onNext(request);
		//requestStream.onCompleted();

		
//		try {
//			latch.await();
//			//latch.await(1, TimeUnit.SECONDS);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	
/*
	public static void clientGrpcService2(String inStr,OutputStream stream) {
	    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext(true).build();
	    GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);

	    CountDownLatch latch = new CountDownLatch(10);
	    
		StreamObserver<ProcessResponse> replyStream = new StreamObserver<ProcessResponse>() {
		    
		    public void onNext(ProcessResponse reply) {
		        
		    	System.out.println(reply);
		        
		        try {
					
		        	stream.write(reply.toString().getBytes());
				
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //assert reply.getMessage().equals("Hello, Armerian World!");
		    }
		 
		    
		    public void onError(Throwable t) {
		        t.printStackTrace();
		        latch.countDown();
		    }
		 
		    
		    public void onCompleted() {
		        System.out.println("We're done!");
		        latch.countDown();
		    }
		};
		 
		// Send the request stream.
		StreamObserver<ProcessRequest> requestStream = grpcService.process(replyStream);
		ProcessRequest request = ProcessRequest.newBuilder().setRequestType(4).setArgs(inStr).build();
		requestStream.onNext(request);
		requestStream.onCompleted();

		
//		try {
//			latch.await();
//			//latch.await(1, TimeUnit.SECONDS);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
*/	


}