/*
 * Copyright 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.protobuf.Timestamp;
import com.wooribank.smarthub.grpc.Grpc;
import com.wooribank.smarthub.grpc.GrpcServiceGrpc;

import io.grpc.stub.StreamObserver;

/**
 * Created by rayt on 5/16/16.
 */
public class GrpcServiceImpl2 extends GrpcServiceGrpc.GrpcServiceImplBase {
  // @aiborisov mentioned this needs to be thread safe. It was using non-thread-safe HashSet
  private static Set<StreamObserver<Grpc.GrpcMessageFromServer>> observers = ConcurrentHashMap.newKeySet();
  private static Set<StreamObserver<Grpc.ProcessResponse>> observers2 = ConcurrentHashMap.newKeySet();
  
  private static ConcurrentHashMap<String,StreamObserver<Grpc.ProcessResponse>> branchInfo = new ConcurrentHashMap<>();
  private static ConcurrentHashMap<String,StreamObserver<Grpc.ProcessResponse>> appInfo = new ConcurrentHashMap<>();
  
  //      Collections.newSetFromMap(new ConcurrentHashMap<>());

  @Override
  public StreamObserver<Grpc.GrpcMessage> grpc(StreamObserver<Grpc.GrpcMessageFromServer> responseObserver) {
    observers.add(responseObserver);

    return new StreamObserver<Grpc.GrpcMessage>() {
      @Override
      public void onNext(Grpc.GrpcMessage value) {
        System.out.println(value);
        Grpc.GrpcMessageFromServer message = Grpc.GrpcMessageFromServer.newBuilder()
            .setMessage(value)
            .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000))
            .build();

        for (StreamObserver<Grpc.GrpcMessageFromServer> observer : observers) {
          observer.onNext(message);
        }
      }

      @Override
      public void onError(Throwable t) {
    	  System.out.println("error!!");
    	  observers.remove(responseObserver);

      }

      @Override
      public void onCompleted() {
        observers.remove(responseObserver);
      }
    };
  }

  
  @Override
  public StreamObserver<Grpc.ProcessRequest> process(StreamObserver<Grpc.ProcessResponse> responseObserver) {
    observers2.add(responseObserver);

    return new StreamObserver<Grpc.ProcessRequest>() {
      @Override
      public void onNext(Grpc.ProcessRequest request) {
        
    	  System.out.println("request ==> " + request);

		  JSONParser parser = new JSONParser();
	      JSONObject argObject = null;

    	  // 1. 인증
    	  if(request.getRequestType() == 1) {
    	    
	
			  try {
				  argObject = (JSONObject) parser.parse(request.getArgs());
				
			  } catch (ParseException e) {
				  System.out.println("branchID insert error!!!");
		
			  } 
			  String branch_id = (String) argObject.get("branch_id");
			
			  if(branchInfo.containsKey(branch_id))
				  branchInfo.remove(branch_id);
			  
						  
			  branchInfo.put(branch_id,responseObserver);
	
	    	  Grpc.ProcessResponse message = Grpc.ProcessResponse.newBuilder()
	                .setStatus(200)
	                .setMessage("ok")
	                .build();
	
	    	  responseObserver.onNext(message);
	
    	  // 2. 헬스체크 
    	  }else if(request.getRequestType() == 2) {
    		  
			  try {
				  argObject = (JSONObject) parser.parse(request.getArgs());
				
			  } catch (ParseException e) {
				  System.out.println("branchID insert error!!!");
		
			  } 
			  String branch_id = (String) argObject.get("branch_id");
			
			  if(branchInfo.containsKey(branch_id))
				  branchInfo.remove(branch_id);
			  
						  
			  branchInfo.put(branch_id,responseObserver);
	
	    	  Grpc.ProcessResponse message = Grpc.ProcessResponse.newBuilder()
	                .setStatus(200)
	                .setMessage("ok")
	                .build();
	
	    	  responseObserver.onNext(message);

    		  
    		  System.out.println("2.health check request ==> " + request);  
    	  // 3. 발권/순번 호출
    	  }else if(request.getRequestType() == 3) {
    		 
    		  System.out.println("3.responseObserver==> " + responseObserver); 
    		  System.out.println("3.==> " + request);  
    		  
    		  try {
				  
    			  argObject = (JSONObject) parser.parse(request.getArgs());
				  
    			  String session_id = (String) argObject.get("session_id");
    			  String issue_source = (String) argObject.get("issue_source");
    			  String branch_id = (String) argObject.get("branch_id");
    			  
				  if(session_id !=null && issue_source.equals("M")) {

					  StreamObserver<Grpc.ProcessResponse> observer = appInfo.get(session_id);
	
			    	  Grpc.ProcessResponse message = Grpc.ProcessResponse.newBuilder()
			                .setStatus(200)
			                .setMessage(request.getArgs())
			                .build();
			          
			          observer.onNext(message);
			          System.out.println("3. sent to app!!!");
				  }else { 
					  
					  System.out.println("3.session id is NULL ");
				  }
		          
				  StreamObserver<Grpc.ProcessResponse> observer2 = branchInfo.get(branch_id);
				  Grpc.ProcessResponse message = Grpc.ProcessResponse.newBuilder()
			                .setStatus(200)
			                .setMessage("ok")
			                .build();
			          
				  //observer2.onNext(message);
				  responseObserver.onNext(message);
				  System.out.println("3. ok!!!");
				  
			  } catch (Exception e) {
				  System.out.println("3. process error!!!");
//		
//
//
//    		  } catch (ParseException e) {
//				  System.out.println("3. process error!!!");
//		
			  } 
			 
			

    		  
    	  // 4. app에서 발권 요청
    	  }else if(request.getRequestType() == 4) {
    		  
    		  System.out.println("4.responseObserver==> " + responseObserver); 
    		  System.out.println("4.==> " + request);  

    		  String uniqueID = UUID.randomUUID().toString();
			  
    		  try {
				  argObject = (JSONObject) parser.parse(request.getArgs());
				  String branch_id = (String) argObject.get("branch_id");
				  
				  argObject.put("session_id", uniqueID);
				  appInfo.putIfAbsent(uniqueID,responseObserver);
				  
				  String args = argObject.toString();
				  System.out.println("4.args ==>" + args);
				  
				  StreamObserver<Grpc.ProcessResponse> observer = branchInfo.get(branch_id);

				  if(observer != null) {
					  Grpc.ProcessResponse message = Grpc.ProcessResponse.newBuilder()
			                .setStatus(200)
			                .setMessage(args)
			                .build();
			          
			          observer.onNext(message);
				      System.out.println("4.sent  " + observer );
				  }
		          
			  } catch (ParseException e) {
				  System.out.println("4. process error!!!");
		
			  } 
			
    	  
    	  }
    	
        
    	for(String s :branchInfo.keySet()) {
    		System.out.println("responseObserver==> " + responseObserver); 
    		System.out.println("branchInfo ===> " + branchInfo.get(s));
        }

    	for(String s :appInfo.keySet()) {
        	System.out.println("appInfo ===> " + appInfo.get(s));
        }

//    	Grpc.ProcessResponse message = Grpc.ProcessResponse.newBuilder()
//            .setStatus(200)
//            .setMessage(request.getArgs())
//            .build();
//
//        for (StreamObserver<Grpc.ProcessResponse> observer : observers2) {
//          observer.onNext(message);
//        }
      
      }

      @Override
      public void onError(Throwable t) {
    	  System.out.println("error!!");
    	  observers2.remove(responseObserver);
      }
      

      @Override
      public void onCompleted() {
        observers2.remove(responseObserver);
    	

        System.out.println("onCompleted ");

        
//        for(String s :branchInfo.keySet(responseObserver)) {
//        	branchInfo.remove(s);
//        	System.out.println("remove branch info ===> " + s);
//        }
//
//    	for(String s :appInfo.keySet(responseObserver)) {
//    		appInfo.remove(s);
//    		System.out.println("remove app info ===> " + s);
//    	}

      
      }
    };
  }


}
