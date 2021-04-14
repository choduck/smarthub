import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.imageio.stream.FileImageOutputStream;

import com.google.protobuf.ByteString;
import com.wooribank.smarthub.grpc.MessageType;
import com.wooribank.smarthub.grpc.ProcessRequest;
import com.wooribank.smarthub.grpc.ProcessResponse;
import com.wooribank.smarthub.grpc.SmartHubServerImpl;
import com.wooribank.smarthub.grpc.VerifiableCredentialGrpc;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class SmartHubServer2 {

	private ManagedChannel channel;
	private VerifiableCredentialGrpc.VerifiableCredentialBlockingStub blockingStub;
	private Server server;
	
	private void start() throws IOException, URISyntaxException {
		/* The port on which the server should run */
		int port = 50051;
    
        
		server = NettyServerBuilder.forPort(port)
					/*
					.sslContext(GrpcSslContexts.forServer(ca, key)
					.trustManager(crt)
					.clientAuth(ClientAuth.REQUIRE)
					.build())
					*/
				.addService(new SmartHubServerImpl())
				.build()
				.start();
		
		System.out.println("Server started, listening on " + port);
    
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				SmartHubServer2.this.stop();
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
		final SmartHubServer2 server = new SmartHubServer2();
		server.start();
		server.blockUntilShutdown();
	}

	/*
	public void VerifiableCredentialClientInit(String json) throws IOException {
		channel = (ManagedChannelBuilder.forAddress("15.164.62.127", 50051)
        .usePlaintext()
        .build());
		
		blockingStub = VerifiableCredentialGrpc.newBlockingStub(channel);
		
		ByteString bs = qrRequestToAgent(json);
		
		byteStringToPng(bs.toByteArray());
	}
	*/
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
		builder.setRequestType1(MessageType.QRCODE_REQUEST_TO_AGENT_VALUE);  // 7
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
