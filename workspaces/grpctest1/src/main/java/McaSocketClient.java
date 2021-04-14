import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import com.google.protobuf.ByteString;
import com.wooribank.smarthub.grpc.Grpc;
import com.wooribank.smarthub.grpc.Grpc.GrpcMessage;
import com.wooribank.smarthub.grpc.Grpc.GrpcMessageFromServer;
import com.wooribank.smarthub.grpc.GrpcServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class McaSocketClient {
    
	public ManagedChannel channel;
	public GrpcServiceGrpc.GrpcServiceBlockingStub blockingStub;


	
	public static void main(String[] args) {
//        if(args.length != 2) {
//            System.out.println("사용법 : java ChatClient id 접속할 서버 ip");
//            System.exit(1);
//        }
        Socket sock = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        boolean endflag = false;
        try {
            sock = new Socket("127.0.0.1", 20301);//아아디,포트
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            
            JSONObject hubInfo = new JSONObject();
            
            //정보 입력
            //영업점ID
            hubInfo.put("branch_id", "10101010");
            //스마트허브IP
            hubInfo.put("hub_ip", "192.168.000.016");
            //요청 테스크 설정
            hubInfo.put("task_id", "1");
            
            String hubInfoStr = hubInfo.toJSONString();
            
            
            pw.println(hubInfoStr);
            pw.flush();
            InputThread it = new InputThread(sock,br);
            it.start();
            String line = "";

            
            for(int i=0 ; i < 10;i++) {
	            try {
	                
	            	hubInfo = new JSONObject();
	                
	                //정보 입력
	                //영업점ID
	                hubInfo.put("branch_id", "10101010");
	                //스마트허브IP
	                hubInfo.put("hub_ip", "192.168.000.016");
	                //요청 테스크 설정
	                hubInfo.put("task_id", "1");
	                
	                hubInfoStr = hubInfo.toJSONString();

	            	pw.println(hubInfoStr);
	                pw.flush();
	            	System.out.println("Saying hi");
		    	    Thread.sleep(2000);
	
		        }catch (InterruptedException e) {
		        	System.out.println("Saying hi");;
		        }	  
            }
            
            //            pw.println(line);
//            pw.flush();

            //clientSample();
//            GrpcClientInit("sssssssss");
            while((line = keyboard.readLine()) != null) {
//                pw.println(line);
//                pw.flush();
//                if(line.equals("/quit")) {
//                    endflag = true;
//                    break;
//                }
            }
            System.out.println("클라이언트 접속 종료");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(pw != null) {
                    pw.close();
                }
                if(br != null) {
                    br.close();
                }
                if(sock != null) {
                    sock.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	public static void GrpcClientInit(String json) {
	    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext(true).build();
	    GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);
	    
	    CountDownLatch latch = new CountDownLatch(1);
	    
	    StreamObserver<Grpc.GrpcMessage> grpc = grpcService.grpc(new StreamObserver<Grpc.GrpcMessageFromServer>() {
	     
	    	
		     @Override
		      public void onNext(Grpc.GrpcMessageFromServer value) {
		    	  System.out.println(value);
		      }
	
		      @Override
		      public void onError(Throwable t) {
		        t.printStackTrace();
		        System.out.println("Disconnected");
		      }
	
		      @Override
		      public void onCompleted() {
		        System.out.println("Disconnected");
		      }
	    
	    });

	    
	    
	    System.out.println("88888888888");	
	    grpc.onNext(Grpc.GrpcMessage.newBuilder().setFrom("vvv").setMessage("kkkbbbbbbbbb").build());
	    
	    //grpc.onCompleted(); 
	    
	    //channel.shutdown();
		
		try {
			latch.await();
			//latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		GrpcMessage request = GrpcMessage.newBuilder().setFrom("ArmerianWorld").setMessage("hello").build();
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
	
	
	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}



}
 
class InputThread extends Thread{
    private Socket sock = null;
    private BufferedReader br = null;
    public InputThread(Socket sock,BufferedReader br) {
        this.sock = sock;
        this.br = br;
    }
    public void run() {
        try {
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null) {
                    br.close();
                }
                if(sock != null) {
                    sock.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	

}

