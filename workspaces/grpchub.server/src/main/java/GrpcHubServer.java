import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.imageio.stream.FileImageOutputStream;

import com.google.protobuf.ByteString;
import com.wooribank.smarthub.grpchub.GrpcHubGrpc;
import com.wooribank.smarthub.grpchub.GrpcHubServerImpl;
import com.wooribank.smarthub.grpchub.MessageType;
import com.wooribank.smarthub.grpchub.ProcessRequest;
import com.wooribank.smarthub.grpchub.ProcessResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class GrpcHubServer {

	private ManagedChannel channel;
	private GrpcHubGrpc.GrpcHubBlockingStub blockingStub;
	private Server server;
	
	private void start() throws IOException, URISyntaxException {
		/* The port on which the server should run */
		int port = 50051;
    
		InputStream crt = this.getClass().getClassLoader().getResourceAsStream("rootca.crt");
        InputStream key = this.getClass().getClassLoader().getResourceAsStream("statistic_server.pem");
        InputStream ca = this.getClass().getClassLoader().getResourceAsStream("statistic_server.crt");
        
     	System.out.println("22222222222222211111111");
    	
        
		server = NettyServerBuilder.forPort(port)
					/*
					.sslContext(GrpcSslContexts.forServer(ca, key)
					.trustManager(crt)
					.clientAuth(ClientAuth.REQUIRE)
					.build())
					*/
				.addService(new GrpcHubServerImpl())
				.build()
				.start();
		
		System.out.println("Server started, listening on " + port);
    
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				GrpcHubServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		final GrpcHubServer server = new GrpcHubServer();
		server.start();
		server.blockUntilShutdown();
	}

//	public void VerifiableCredentialClientInit(String json) throws IOException {
//		channel = (ManagedChannelBuilder.forAddress("15.164.62.127", 50051)
//        .usePlaintext()
//        .build());
//		
//		blockingStub = GrpcHubGrpc.newBlockingStub(channel);
//		
//		ByteString bs = qrRequestToAgent(json);
//		
//		byteStringToPng(bs.toByteArray());
//	}
	
	public void byteStringToPng(byte[] bs) throws IOException {
		File f = new File("/var/lib/tomcat8/webapps/ROOT/resources/images/QR/qr-code.png");
		
		if (f.exists()) {
			f.delete();
		}
		
		FileImageOutputStream imageOutput = new FileImageOutputStream(f);
	    imageOutput.write(bs, 0, bs.length);
	    imageOutput.close();
	}
	
	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public ByteString requestProcess(String json) {
		ProcessRequest.Builder builder = ProcessRequest.newBuilder();
		builder.setRequestType(MessageType.QRCODE_REQUEST_TO_AGENT_VALUE);  // 7
		builder.setArgs(json);
		ProcessRequest req = builder.build();
		ProcessResponse resp;
	
		try {
			resp = blockingStub.process(req);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
		System.out.println("# resp : " + resp);
		System.out.println("# resp.getPayload() : " + resp.getPayload());
		return resp.getPayload();
	}
  
	public ByteString qrRequestToAgent(String json) {
		return requestProcess(json);
	}
}
