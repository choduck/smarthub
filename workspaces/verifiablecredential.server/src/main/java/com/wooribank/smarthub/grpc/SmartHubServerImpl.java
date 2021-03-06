package com.wooribank.smarthub.grpc;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.stream.FileImageOutputStream;

import java.util.Set;
import java.util.StringTokenizer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonElement;
import com.google.protobuf.ByteString;
import com.wooribank.smarthub.grpc.ProcessRequest;
import com.wooribank.smarthub.grpc.ProcessResponse;
import com.wooribank.smarthub.grpc.VerifiableCredentialGrpc;

import io.grpc.stub.StreamObserver;

public class SmartHubServerImpl extends VerifiableCredentialGrpc.VerifiableCredentialImplBase {
	
	String db_driver = "org.mariadb.jdbc.Driver";
	String db_host = "52.79.127.56";
	String db_user = "blockchain";
    String db_pass = "blockchain!@";
    String db_name = "BLOCKCHAIN";
    String db_url = "jdbc:mariadb://" + db_host + "/" + db_name;
    
    int ERROR   = 1;
    int SUCCESS = 0;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void process(ProcessRequest request, StreamObserver<ProcessResponse> responseObserver) {
	    
    	
	    int reqType = request.getRequestType1();
	    String args = request.getArgs();
	
	    System.out.println("# args : " + args);
	    JSONParser parser = new JSONParser();
	    JSONObject argObect = null;
 
	    
        JSONObject ret = new JSONObject();
        JSONArray credentialTypeList = new JSONArray();
         

    	JSONObject jsonObj = new JSONObject();
            
         jsonObj.put("id", 				"aaa");
         jsonObj.put("type", 			"bbbb");
         jsonObj.put("tag", 				"tag");
         jsonObj.put("description", 	"description");
            
         credentialTypeList.add(jsonObj);

         
        ret.put("credential_type_list", credentialTypeList);
         
        System.out.println("### credential_type_list : " + ret.toJSONString());
         
         
        SUCCEED( responseObserver, ByteString.copyFromUtf8(ret.toJSONString()) );

	    
	    
	    /*       
	    if (reqType == 2) {	// ?????? ?????? ??????
	    	Connection conn= null;
            PreparedStatement stmt = null;
            StringBuffer sb = null;
            		
            try {
            	Class.forName(db_driver);
            	conn = DriverManager.getConnection(db_url, db_user, db_pass);
                 
            	sb = new StringBuffer();
                sb.append(" SELECT	A.certiCd AS id,                             							\n");
                sb.append("         CASE WHEN A.certiCd = '10001' THEN 'MobileID'   			\n");
                sb.append("         ELSE 'MobileLicense'                             						\n");
                sb.append("         END AS type,                                     						\n");
                sb.append("         CASE WHEN A.certiCd = '10001' THEN '??????'   					\n");
                sb.append("         ELSE '??????'                             										\n");
                sb.append("         END AS tag,                                     							\n");
                sb.append("         (SELECT cdNm                                     						\n");
                sb.append("             FROM code                                    						\n");
                sb.append("             WHERE uppCd = 'B0001'                        					\n");
                sb.append("                 AND cd = A.certiCd                       						\n");
                sb.append("         ) AS description                                 						\n");
                sb.append(" FROM certi_list AS  A                                    						\n");
                    
                stmt = conn.prepareStatement(sb.toString());
                 
                ResultSet rs = stmt.executeQuery();
                 
                JSONObject ret = new JSONObject();
                JSONArray credentialTypeList = new JSONArray();
                 
                while (rs.next()){
                	JSONObject jsonObj = new JSONObject();
                        
                     jsonObj.put("id", 				rs.getString("id"));
                     jsonObj.put("type", 			rs.getString("type"));
                     jsonObj.put("tag", 				rs.getString("tag"));
                     jsonObj.put("description", 	rs.getString("description"));
                        
                     credentialTypeList.add(jsonObj);
                }
                 
                ret.put("credential_type_list", credentialTypeList);
                 
                System.out.println("### credential_type_list : " + ret.toJSONString());
                 
                stmt.close(); 
                conn.close();
                 
                SUCCEED( responseObserver, ByteString.copyFromUtf8(ret.toJSONString()) );
            } catch (ClassNotFoundException ce) {
            	ERROR( responseObserver, "DB Driver Load fail" );   
            } catch (SQLException e) {
                clearConnection(conn, stmt);
                ERROR( responseObserver, "DB processing error" );
            }
	    }  // End of if (reqType == 2) {	// ?????? ?????? ?????? ??????
	    else if (reqType == 4) {	// ?????? ?????? ??????
	    	
	    	try {
	             argObect = (JSONObject) parser.parse(args);
	    	} catch (ParseException e) {
	         	ERROR( responseObserver, "json parsing error" );
	             return;
	    	}
	    	 
	    	if (((JSONArray)argObect.get("args")).size() <= 0) {
	    		ERROR( responseObserver, "request with no data" );
	            return;
	        }
	    	
	    	Connection conn= null;
            PreparedStatement stmt = null;
            StringBuffer sb = new StringBuffer();
            
            try {
            	Class.forName(db_driver);
            	conn = DriverManager.getConnection(db_url, db_user, db_pass);
                
                sb.append(" SELECT	orderNo,					\n");
                sb.append("         fieldNm AS name,        \n");
                sb.append("         fieldKorNm AS title,        \n");
                sb.append("         dataTp AS type,         \n");
                sb.append("         desctn AS description	\n");
                sb.append(" FROM certi_field AS  A         \n");
                sb.append(" WHERE certiCd = ?              \n");
                
                JSONArray argsArray = (JSONArray) argObect.get("args");
                
                JSONObject ret = new JSONObject();
                JSONArray credentialFieldList = new JSONArray();
                
                for (int i=0; i<argsArray.size(); i++) {
	                stmt = conn.prepareStatement(sb.toString());
	                stmt.setString(1, argsArray.get(i).toString().replace("[\"", "").replace("\"]",""));
	                
	                ResultSet rs = stmt.executeQuery();
	                
	                while (rs.next()) {
	                	JSONObject jsonObj = new JSONObject();
	                       
	                	jsonObj.put("orderNo", 	rs.getString("orderNo"));
	                	jsonObj.put("name", 		rs.getString("name"));
	                	jsonObj.put("title", 		rs.getString("title"));
	                	jsonObj.put("type", 			rs.getString("type"));
	                    JSONObject obj = (JSONObject)new JSONParser().parse(rs.getString("description"));
	                    jsonObj.put("description", 	obj);
	                	credentialFieldList.add(jsonObj);
	                }
    			}
               
                ret.put("credential_field_list", credentialFieldList);
                
                System.out.println("### credential_field_list : " + ret.toJSONString());
                
    			stmt.close();
                conn.close();
                
                SUCCEED( responseObserver, ByteString.copyFromUtf8(ret.toJSONString()) );
            } catch (ClassNotFoundException ce) {
            	ERROR( responseObserver, "DB Driver Load fail" );       
            } catch (SQLException e) {
                clearConnection(conn, stmt);
                ERROR( responseObserver, "DB processing error" );
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }  // End of if (reqType == 4) {	// ?????? ?????? ?????? ??????
	    else if (reqType == 6) {	// ?????? ??????
	    	
	    	try {
	             argObect = (JSONObject) parser.parse(args.replace("[", "").replace("]",""));
	    	} catch (ParseException e){
	         	ERROR( responseObserver, "json parsing error" );
	             return;
	        }
	    	 
	    	if (argObect.get("args").equals("")) {
	    		ERROR( responseObserver, "request with no data" );
	            return;
	        }
	        
	    	Connection conn= null;
            PreparedStatement stmt = null;
            StringBuffer sb = new StringBuffer();
            
            try {
            	
            	Class.forName(db_driver);
            	conn = DriverManager.getConnection(db_url, db_user, db_pass);
            	
            	sb.append(" SELECT 											\n");
    			sb.append(" EXISTS (SELECT *			    			\n");
    			sb.append("            	   FROM member_certi_field   	\n");
    			sb.append("            	 WHERE fieldNm = ?				\n");
    			sb.append("            	     AND fieldCnt = ?				\n");
    			sb.append("            ) AS existsFlg 						\n");
                
            	JSONParser p = new JSONParser();
    			JSONObject dataObect = null;
    			
    			try {
    				dataObect = (JSONObject) p.parse(argObect.get("args").toString());
	   	    	} catch (ParseException e){
	   	    		ERROR( responseObserver, "json parsing error" );
	   	    		return;
	   	        }
    			
    			Set<Entry<String, JsonElement>> keys = dataObect.keySet();
    			Iterator i = keys.iterator();
                List<String> listExists = new ArrayList<String>();
                
                while (i.hasNext()) {
                	String key = i.next().toString();
                	
                	if ("picture".equals(key)) continue;
                	
                	String value = (String)dataObect.get(key);
                	
                	stmt = conn.prepareStatement(sb.toString());
	                stmt.setString(1, key);
	                stmt.setString(2, value);
	                
	                ResultSet rs = stmt.executeQuery();
	                
	                if (rs.next()) {
	                	listExists.add(rs.getString("existsFlg"));
	                }
                }
                
                String existsFlg = "";
                for (int j=0; j<listExists.size(); j++) {
                	if ( "0".equals(listExists.get(j)) ) {
                		existsFlg = "N";
                		break;
                	}
                }
                
                if ( "N".equals(existsFlg) ) {
                	ERROR( responseObserver, "Membership information does not match.");
                } else {
                	SUCCEED( responseObserver, ByteString.copyFromUtf8(args) );
                }
               
               stmt.close();
               conn.close();
            } catch (ClassNotFoundException ce) {
            	ERROR( responseObserver, "DB Driver Load fail" );      
            } catch (SQLException e) {
                clearConnection(conn, stmt);
                ERROR( responseObserver, "DB processing error" );
            }
            
	    }  // End of if (reqType == 6) {	// ?????? ?????? ??????
	    else if (reqType == 9) {	// ?????? ??? ?????? ??????
	    	
	    	try {
	    		argObect = (JSONObject) parser.parse(args);
	    	} catch (ParseException e){
	         	ERROR( responseObserver, "json parsing error" );
	             return;
	        }
	    	 
	    	JSONObject jsonObject = null;
	    	
	    	try {
	    		jsonObject = (JSONObject) parser.parse(((JSONArray) argObect.get("args")).get(1).toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	JSONObject dataObject = (JSONObject) jsonObject.get("claim");
	    	System.out.println((JSONObject) jsonObject.get("claim"));
	    	System.out.println(dataObject.get("picture"));
	    	
	    	String picture = dataObject.get("picture").toString();
	    	byte[] bs = picture.getBytes();
	    	System.out.println(bs);
	    	
	    	try {
				byteStringToPng(bs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	String[] type = picture.split("_");
	    	System.out.println(type[0]);
	    	
	    	JSONObject retObject = new JSONObject();
	    	JSONArray retJsonArray = new JSONArray(); 
	    	retJsonArray.add("info");
	    	retJsonArray.add((JSONObject) jsonObject.get("claim"));
	    	System.out.println(retJsonArray);
	    	
	    	retObject.put("args", retJsonArray);
	    	System.out.println(retObject);
//	    	
//	    	try {
//				webSocketConn(retObject.toJSONString());
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    }
*/
	
	}
    
    public void byteStringToPng(byte[] bs) throws IOException {
    	System.out.println(bs);
		//File f = new File("/var/lib/tomcat8/webapps/ROOT/resources/images/photo/photo.png");
    	File f = new File("D:/photo/photo.png");
    	byte[] photo = Base64.getDecoder().decode(bs);
		
		if (f.exists()) {
			f.delete();
		}
		
		FileImageOutputStream imageOutput = new FileImageOutputStream(f);
	    imageOutput.write(photo, 0, photo.length);
	    imageOutput.close();
	}
    
    public void webSocketConn(String claimJson) throws URISyntaxException {
    	WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://localhost:8887")) {

		    @Override
		    public void onOpen(ServerHandshake serverHandshake) {
		        // WebSocket ?????? ?????? ??? ?????? ??????, ????????? Hello ????????? ??????
		        System.out.println("*** onOpen ***");
		        this.send(claimJson);
		    }

		    @Override
		    public void onMessage(String message) {
		    	System.out.println("*** message : " + message);
		        // WebSocket ???????????? ????????? ????????? ?????? ??????, ????????? Hello ????????? ????????? ?????? ??????
		        //if (message.equals("Hello")) {
		        //    this.close();
		        //}
		    }

		    @Override
		    public void onClose(int code, String reason, boolean remote) {
		        // ?????? ?????? ?????? ??? ?????? ??????
		    	 System.out.println("*** onClose ***");
		    }

		    @Override	
		    public void onError(Exception ex) {
		        // ?????? ????????? ?????? ??????
		    }

		};
		
		// ?????? ????????? WebSocket ????????? ????????????.
		webSocketClient.connect();
    }
    
    private void ERROR(StreamObserver<ProcessResponse> responseObserver, String message){
    	ProcessResponse.Builder builder = ProcessResponse.newBuilder();
    	builder.setStatus(ERROR);
		builder.setMessage(message.toString());  
		ProcessResponse resp = builder.build();
		  
	    responseObserver.onNext(resp);
	    responseObserver.onCompleted();
    }
    
    private void SUCCEED(StreamObserver<ProcessResponse> responseObserver, ByteString message){
        ProcessResponse.Builder builder = ProcessResponse.newBuilder();
        builder.setStatus(SUCCESS);
        builder.setPayload(message);
		ProcessResponse resp = builder.build();
		  
	    responseObserver.onNext(resp);
	    responseObserver.onCompleted();
    }
    
    private void clearConnection(Connection conn, PreparedStatement stmt){
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
