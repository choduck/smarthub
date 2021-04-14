import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import javax.imageio.stream.FileImageOutputStream;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;


public class SocketServer extends WebSocketServer {
	
	VerifiableCredentialServer verifiableCredentialServer = new VerifiableCredentialServer();
	
	public SocketServer(int port) {
        super(new InetSocketAddress(port));
    }
	
	@Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("### onOpen ###");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    	 System.out.println("### onClose ###");
    }

	@SuppressWarnings("unchecked")
	@Override
    public void onMessage(WebSocket conn, String message) {
		System.out.println("### message : " + message);
		
		JSONObject msgObject = null;
		JSONParser parser = new JSONParser();
		
		try {
			msgObject = (JSONObject) parser.parse(message);
    	} catch (ParseException e){
        }
		
		System.out.println("# msgObject : " + msgObject);
		System.out.println( ((JSONArray) msgObject.get("args")).get(0) );
		System.out.println( ((JSONArray) msgObject.get("args")).get(1) );
		
		String msgType = ((JSONArray) msgObject.get("args")).get(0).toString();
		
		if (msgType.equals("qr")) {
			//JsonArray jsonArray = new JsonArray();
	        //jsonArray.add(message);
	        
			JSONObject dataObject = new JSONObject();
			dataObject.put("args", ((JSONArray) msgObject.get("args")).get(1) );
	        
	        try {
				verifiableCredentialServer.VerifiableCredentialClientInit(dataObject.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        broadcast("QR");
			
		} else {
			broadcast(((JSONArray) msgObject.get("args")).get(1).toString() );
		}
    }
	
    public static void main(String[] args) throws InterruptedException, IOException {
        int port = 8887;
        final SocketServer server = new SocketServer(port);
        server.start();
        System.out.println("ChatServer started on port: " + server.getPort());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
    }
}
