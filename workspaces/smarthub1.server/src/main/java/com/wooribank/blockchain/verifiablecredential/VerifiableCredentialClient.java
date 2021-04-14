package com.wooribank.blockchain.verifiablecredential;

import java.util.concurrent.TimeUnit;

import com.wooribank.blockchain.verifiablecredential.MessageType;
import com.wooribank.blockchain.verifiablecredential.ProcessRequest;
import com.wooribank.blockchain.verifiablecredential.ProcessResponse;
import com.wooribank.blockchain.verifiablecredential.VerifiableCredentialGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class VerifiableCredentialClient {

	private final ManagedChannel channel;
	private final VerifiableCredentialGrpc.VerifiableCredentialBlockingStub blockingStub;

	/** Construct client connecting to VerifiableCredential server at {@code host:port}. */
	public VerifiableCredentialClient(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
	}

	/** Construct client for accessing VerifiableCredential server using the existing channel. */
	VerifiableCredentialClient(ManagedChannel channel) {
		this.channel = channel;
		blockingStub = VerifiableCredentialGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void requestProcess(String retJsonStr) {
		ProcessRequest.Builder builder = ProcessRequest.newBuilder();
		//builder.setRequestType(MessageType.CREDENTIAL_TYPE_LIST_TO_WAS_VALUE);	// 2
		//builder.setRequestType(MessageType.CREDENTIAL_FIELD_LIST_TO_WAS_VALUE);	// 4
		//builder.setRequestType(MessageType.CREDENTIAL_REQUEST_TO_WAS_VALUE);	// 6
		builder.setRequestType1(MessageType.CREDENTIAL_PRESENT_TO_WAS_VALUE);	// 9
		
		builder.setArgs(retJsonStr);
		ProcessRequest req = builder.build();
		ProcessResponse resp;
	
		try {
			resp = blockingStub.process(req);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	
		System.out.println(resp);
		System.out.println(resp.getPayload().toStringUtf8());
		System.out.println(resp.getMessage());
	}
  
	/**
	 * Greet server. If provided, the first element of {@code args} is the name to use in the greeting.
	 */
	public static void main(String[] args) throws Exception {
		//VerifiableCredentialClient client = new VerifiableCredentialClient("52.79.127.56", 50051);
		//VerifiableCredentialClient client = new VerifiableCredentialClient("15.164.62.127", 50051);
		VerifiableCredentialClient client = new VerifiableCredentialClient("localhost", 50051);
		try {
			/*
			String retJsonStr = "{\"args\":["
				+ "10001"
				+ "]}";
			*/
			/*
			String retJsonStr = "{\"args\":["
					+ "\"{\"picture\":\"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\","
					+ "\"korNm\":\"woori\","
					+ "\"residNo\":\"ㄷㄱㅅㄱㅇㄱ\","
					+ "\"address\":\"ㄷㄱㅅㄱㅇㄱ\","
					+ "\"issuedDate\":\"1990.2.20\","
					+ "\"issuedPlace\":\"ㄷㄱㅅㄱㅇㄱ\""
					+ "}\"]}";
			*/
			
			String retJsonStr = "{\"args\":[\"12345\",\"{\\\"claim\\\":{\\\"id\\\":\\\"did:woori:10330\\\",\\\"10002_issuedplace\\\":\\\"1234\\\",\\\"10002_picture\\\":\\\"\\/9j\\/4AAQSkZJRgABAQAAAQABAAD\\/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkzODdASFxOQERXRTc4UG1RV19iZ2hnPk1xeXBkeFxlZ2P\\/2wBDARESEhgVGC8aGi9jQjhCY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2P\\/wAARCAMkAX0DASIAAhEBAxEB\\/8QAGgABAQADAQEAAAAAAAAAAAAAAAECAwQGBf\\/EAEUQAQACAQIEAgQKCAUDAwUAAAABAgMEEQUSITFBURMiYXEUFRYyVFWBkZOUBiM1QnKhseEzQ1LB0XOC0jRTYiQ2kqLw\\/8QAFwEBAQEBAAAAAAAAAAAAAAAAAAECA\\/\\/EABwRAQEBAQEBAQEBAAAAAAAAAAARARICMSFBUf\\/aAAwDAQACEQMRAD8A8QA6sAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOvQxp8l64cmmyZst7bV5cvL\\/tJq9Pi+E5Y0lotjjL6OlJtva3tjp2\\/5YaPUV0tsmTlmcvJNccx+7M9N\\/u3TS6j4LaubFv6elt6zMRNdtv6oO\\/JwzTUy6HFOaZnLa1cto7bxMdI+\\/bdp4no8enxYb1xZNPkvNonDktvMRHa3aO\\/X7m2nGdvgs20+OZw2tNuWsV338vL\\/AJ2c+r1WLJpcWnw+ltWl7X58sxv126R7OgOIBQAAAAAAAAAAAAenx8F4fbQabV6n0mHHm5aRteI2339a0zDzD0Wh\\/Seuk0OLS30fpq467RNr+P3Jo06rhdK8c02gw0rMzEbzNpiuSO+\\/nG8Q3azgePQanDhvgyZa5tRXkvW37u0709\\/br4+x8ziHFMmr4jGtxc2HJtERtb5u0bdG\\/wCPs2XTYMOrr6f0OeuWLTbaZiInpP3guPQ6XLjz3vlrTl1UY6zWJ5ZrO\\/SI+xtz8M0tMGS3JakVrkmMs26c1bzEV29sQ+dGsp6bNkyYYyzktNom228b7\\/Z5LfW4rxb\\/AOkx7zTlj2dNtwbtNGlrj0uS\\/oZ9W0Za2777ztO33MNf8HnSae2GMcXmsTkivffq16HU4NPTURmw2yTkxzSu07bTvH\\/BxDXW1s4ZtWI9HiinSIjfb3A0YMNs+WtKxO02iJtt0jedurq4jpaaXDporHrzF4vPnMXmP9k02tppqbVw9ZivNPN3mL82\\/wB20MdfrfhkYtqcvJzeO++9pn\\/cHX8X6eaegiuX0\\/wb4R6Xm9Wem+223bw337saaPS5dBky0pmrGPHzfCLTtW1+nq7bf7teHid8OhtgrfNa1qzTabepWJ77R7lprdNiw5PQ4slcmXF6O1Obenbrbz9uwMs2h086f0mK+1aRva\\/PFubpG3Twnfpt\\/wAHAOG04pr7Ycl5pFMc36RvvtMRt\\/Nq0evrp6Rjmlq1mJ3vTrbmntMe6On2y6uEcXw6Di2fV3xWnHkrasVptvG8xP8AsDZ+kfBsfC64cmPJNvSTMTG20Rts+G+5+kXG8HFseCuHFkp6OZmefbx28nwzAAUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABUQAAAAAAAAAAAFAAAAGVKxbveIn3Mr44pvHPEzHhsxx15rxHh4s88b25461t4p\\/RjekVrWfOF5KRWtrXmN\\/YuSN6Yo9jKItyxWYxzt5ylVjXHS07VvO\\/uanRtas\\/NxxPvaLV5Z26T7lxGVKc28zO1Y7yWpHLNqW3iO+647V5bUt0ifFd60paKzzTYGNqbY628ylaTHW0xPlEMr\\/wCDT7VrtWvS9Inz26il8VKbc15+5qnbedusNtfV3\\/WVmJ8JYXiObpMT7jBgAqAAAAAAAAAAAAAoCKAoAiAACKAgoCCoAAAAAAAADKLzFOWNo37z5lbzWs17xPhLEBsyfMx+5ZrFqU2msT47zs1BBtvWb25uakf9zVtsAM8W0ZK79t\\/FlOPe0zFqfe1ANl4tFYibVmI7bSyx\\/wCH6m3Pv4tIDoj0m\\/6zbl8eZpttzTy9t+jEAAUAAAAAABQEUAAVFRQAAAFBERQEFQAAAAAZUrzT4\\/ZDZakzEepMbR02KNIzxz5xHLHfobb1p1iOv+4NY3zHWI28Y69EiI9Jbb+ZRpG68TyzvO\\/tlhije3bePcDAdGSLTXrXr4d5YUrWfR7959nfqUjUNto9XHEdes9obJjrO0dP\\/wC9iUjmGUxPNtMdW21ZmJ9Wdp7Ry9lGgbpiIm9tomY27sprEVttt4\\/0Ksc4zxxHNO+3bxZ12mZ9aJ6T2jYqNI24oiYt0npG6RETSNo\\/e7FVrHRy1nft2mN\\/Jryx+tmPcUaxvjpG0xvPnFezCtd80RPWO\\/Yo1jfWsRWfPefD2MKx6nq7c2\\/XcowGd9unbfbrsxBDZQAAAAAAAAFFBEABBQERQEABaTEW69YWeTbpNt2ICzadojwJnetY8kAZxkneN+3uK2iL2md9p7MAGy96zWYjf7mOO0VtvPZiA2TkiazG8zPuiGMXtWIiJ7exiCsub1ax4xuym1JmZ3vDWCLM9Vm29Kx16MQGU39eZjx82VcnqzE9536tYKsWms7wyi87zv5T2hgCLzTtEb7bLN96+3fdiAz9JPXfynsxvbmvNoQBlF5ito3neU5p2nr3QFbK5Om1uvt+xIvEV25YlgoMptFp3mv82KgAIAAAAAAKIAMwBgABBUFEUBEUBAAAAAAAAAAAAAAAAAAAFAAAFRRQABAAAABQQAAAABmAMAAAAIKCoigIigIAAAAAAAAAAAAAAAAAKAoCgACAAAAgqoAACgAAADMQRhQAAAAAQAURQERQEFQAAAAAAAAUAAAABQFAAEAAFAQAAABQAAAAABkCowAAAAAAIqAACiKAiMkBBUAAAZVpa3za7tmOMda8153nyWct7ztjrtCVUjTW8bRC+gpHfJB6G9vn22PQUjvkSqfB4n5t92vJitj6ztMNnwf\\/AE3Y3xZIjrvMKjUN2O2Pl5b1+0yYZr1r1qtGpQABAABQQAAAAUEAAAQAFAAbAEYAAAAAAAAABQAEFQEFQCtZtO0d23LSlKRHe7XS80neG3HT\\/MyfzTVTHh3jmv0qs5v3cUJM2z32jpVlNqYY5axvbzRU9Flv1tO3vYZMfJt62+7KK5cvWZ2j2r8H87qMMeObxMxMRsz5suLv1j2k4LV61sRlvSeW8bx7QZepm\\/8AjZjW1sNtrdYW2OLRz4\\/uWtoy15bfO8JQY5ccbc9OzU247TS80t2lhlpyX28PBcGACggAAKAIAAAAAAAAAADaAjCCgIAAAKAAAAAAAAgrLHyxbewMseOIjnv28kmbZ77R0rCzNs1to6Qt7xiryU7+bLRe8Yq8lO\\/jLR1ievf2t9KRjjnv38IYRW2fJM9oXEXnyZelekexfg8\\/vXiJW+Tl2pi++EjBM9bW2lFPQ3p1pbf3Mq5Iv6mSOvmxmt8PWJ3hjkvF5iYjaQZethv51lcscsxkp2lY\\/WYZifnQmL18VqT9gGaIvSMkfaW\\/WYIt4wYPWpakpp+sWrPiDShPSdhoAAARQAAAAUAAAAAAEGwAYABQAAAAABFQAAAABlSk3naGLKtrV35Z7g2XtGOvJTv5lKRjjnv38ilYpHPfv5MYi2a289IZaSItnvvPSsMr32\\/V4\\/5F77R6PH7liK4a7z1tIERXDXeetpYxS2SJvadoWlZvPPfsmS85J5a9vIGWCeaJpPWNmvHSLXmsy2RthpO\\/W0pgjaLXnsBg6ZLVTB0zTC4P37ymnje9rCtc2ml7cs7dWem\\/xJ9y4OuS1pNP1yWlRqv8+3vYrfreZ9qLiACiAAAAKKCCoACoAAoADMUGEFRAAUAAAAEUBAAAAGVLRW28xuxAbYi2a289IW99vUx\\/yScm9YrSNmURGGu89bSyrXfHNNuvWWVKTb17z09q1rN5579mOS83nlr28lUyXm88te3kyiK4a7z1tJEVw13nraWNKTltz37IFKTltzX7F7TkmKU7JkyTb1KR6seTHHknHE7RG8+Kq2ZZjHjjHXv4n+Fg\\/wDlZMdP8zJPt6pG+fL5VhBlT9Xp5nxlMXqYbX8+yZbekvFK9oXPMVrXHHh3VGgEaFQAAAFNlAAAQAFRUAAUQAbQEYAAEUBAFAAAAEFAQUAABaW5J323Z0rzzz3no1LNp5dt+nkis8mSbzy17eTKIjDXeetpSs1xU5u9pY0pOW3PeeiKtKTltz37F7zknkx9i95yTyY+yzNcFdq9bSKTNcFdo62lMWOIj0mTt5GPH\\/mZPf1SZtnvtHSsATNs99o6Vhle8Y6+jx9\\/GTJeMVeSnfxlMUUpT0lp3nwgRaVjDTnt86fBotM2mZnvK3vN7bz9zFrBAFAU2AU2UEBAVAAAAVBFVAUAAbRUZZAFAABAAAEBAAAAABUBRJJlBRsyZeaIrWNoagHRNq4abV62nxY4qxMTkvPRpEg23vOW8V32qzveMdeTH38Zc4sABQAANlXYE2XZdhBAlJBJBFBUAUAAAUAAABtAZZBBUVAAEAAAAAAAABRJkQUQAAFAAAAAABQBYhdiFQEkJBJYrKKIAAAAqAKAAIAogK3IIjCiAAAAAAAAAAAokiAIAoAoAAgKAAACgKAKAgJKpIMZRUUAAAAAAAAAAAAbAVGQAAAAAAAUBJAljMrMsJlRZlABRAFEBVRQEUABTYBQAAEAAVJAVjKMpQEAAAAAAAAAAABtARAAAAAAAABjMrLGQSUBQQAURQFSGUAmy7LsAmxsoKAAACAACACiAqoAILsbAiLsAgpsCCgIKgAANoCIAAAAAgKgkyBMsZJRQBAAAAAWGUMWUAoAAAAAAICoAAAAAoACiKAAKmwoCGygMUZSgID6Wi4Pk1eCMs5IpWfm7x3EcQCIAgKgAAgEpJMoogAIAAAAACsoYsoBQAAAEFBBUFAAAAAAAQBUAVUAURRQAESWSSDF6\\/hvTh2D+CHkHr+Gfs7B\\/BDPoeUEFZUQBRAAkSVElAAQAAAAAAAVlDGGUAoAAAoAAIAAAAAAAICgAAAgoiigAAAJL13Df2dg\\/gh5KXreG\\/s\\/B\\/BDPoeTEVpkAQAAGMrLFQRUAAAAAAAVFgFhYSGQoAAIAAAAAAAAAAgAAACgioCiCCqxXcVQAHreG\\/s\\/B\\/BDyT13DP2fg\\/ghn0PIiK0yKigEjGQJQAQAAAAAAAFIFgFgAUAAAAAAAAAAEAVAAAAAUEUBAAAAVWK7oqvXcN\\/Z+D+CHkHr+Gfs\\/B\\/BCeh5AQVlkIATKAAgAAAAAAKoCgMoRUAAAQBRAFEAVAAAAAAAAAUAAAAQAAAEVAFew4Z+zsH8EPHvX8M\\/Z2D+CGfSvIAKyAAIqAAAAAAKoACqioAAD7WHgUajg2m1uPU4cd8t71tGfJFI6bbbPjPW6O2m0ug02PNxHSZNJF5mvpdFa3XpzRFphNHzsf6PRGh1uoy6vBedPi561wZYvvO\\/j7HJw\\/huLNo82u1me2HS4bRT1K81r2nwiH39Zq+H5I1WHScT0Om0+eOWa00doty+UzD5HCM8ZKajhNtPfWafLfnr6K3Les139aN+nbwkVq1XCsNKaTU6XPbNpNTk9HvavLelomN4mOsePdo4zo8fD+LanSYrWtTFflibd56OrLxHFnnQ6HR4bYdLgyxaOe3Na9pmN5me33Mf0p\\/+5Nd\\/1P8AaDBjo+HaeeHzr9fqL4sE5PR0pjpzXvbbee8xERDZfg+Ouv4dWme2XR660RTLFeW23NtaJjrtMLwzUY9Rw7Lw\\/U6TLnw4ptqIvhvFb4oiPWnrG0x2bMHEaazjPCcGnwzg0umy1rjpNuaetomZmfORHztRp9Pp+LZ9NlyZK4MWW9OetYtbaJmI6dH2sX6NaTLw+dZXUa3bvXFOmr6S1em9orz9Y6ueufDp\\/wBJtde3wqc06jJGL4PWtpmZtPhaJfVvw3Lk1ldVevGPhUdazObFF490b7\\/YK8lq66emaY0uTJfHt3yUitt\\/dEy0vr\\/pBnx59RHPGtjVU9W\\/wmtYnb7Ijr73yFwABAAAQUVAAAAAAABAAHsOGfs7B\\/BDx72HDP2dg\\/ghn0uPHiorIAAAKAAAKAACooCkCAIAr6+DimhtwnT6HW6PNl9Be962x5op87\\/tnyfHCD6mXUcHtivGLQaquSYnltbUxMRPu5WvgnEa8L4lTVXxTlitbVmsW233jbu+epB9b4TwP6u1f5qP\\/F8\\/VXw5NRa2nx3x4p7VvfmmPt2hpCD6HBuIYuH589s2G2bHmwXw2rW3LO1tt532lujU8DienDtX+aj\\/AMXyQg79FxGvD+MU12mwzNMd5tXHktvO3baZ29vd02ycByZJzW+Mq2meaab0nr\\/FPX7dnxwiu7jPEp4prfT+j9HWtK4615uadojpvPjLhQEVAVQAAAAAAAQAAABAAHsOGfs7B\\/BDx72HDP2dg\\/ghn0uPIIoqIAIACgAACgAAqKCo7MHC+IanFGXBo8+THPa1aTMNeTQanFp8mfJimlMWSMV+bpMWmJnbbv4Sg50fSrwHiVsdLxp4it6xeu+SsbxMbxPWXJrNHqNDmjFqcfo7zWLRG8T0ntPQGgfQrwTiVsMZa6S8xMbxXpzTHnFe\\/wDJx4dPm1GaMOHHa+Sd9qxHUGA7Z4NxKtbWnRZoiImZnl7RDijqCjs1PCdfpNPGfPp7Ux9N53iZrv25oid4+1qtodRXPiwzSPSZq1vSOaOsWjeOoNCPqT+jvFIpF\\/g0csztFvS023+9xafR59TqJwYaRbJG+8c0R29sitA7sXCNbmzeiphibeljD8+vz5iZ27+US4q1m9orWN5mdoEQfU+TvFeSb\\/BfVidt\\/SU23+9wW02amp+DTSfTc3JyxO\\/Xy6A1DqwcN1eoyZMePDM3x3rS8TMRy2meWI6+1hj0eozaqdNiw2yZqzMTWsb7bd1Ggder4ZrNFSL6jBNaT056zFq7+W8bw5EABQAAAAAARUAAAew4Z+zsH8EPHvYcM\\/Z2D+CGfS48gAqAAIKgAAAAAACoKPR8BwRi4fqZ1WX0Ea+k6fTzedome\\/N7t4iN\\/a0zp8uD9HNVp9TE4cldfjrfnj5vqW6vi5MuTJFYyXtbkjlrvO+0eUOn4feeGZNFavNz5q5eeZ69KzG382YPTW02l1sUjPbQZcmn0tZm1vT1mcdaxtbbaPDyfG4vnwfDNLqcObS5q4orSMWKL7RWvbfmjxZfH2G1MfpeG0vkrgrp5vGe9eakRttMRLgy63HXVUz6LS00s0jbl3nJEz16+tv5kH0c+Th2p4nbiU8Sy45tk9LOL0Vpyx132ie327tOizaTX\\/pHkz6yuPFp81st+W9prWJmLTWJmPbs0fHWu\\/14vwMf\\/i1xxTVRmtl3wze0RWebBjmNvdNdlV6CME6Olc9I4Ni9LjvGO\\/p8s7xMTWdt59svL6bN8H1OLNFYt6O8W2ntO07u63HdfelKXtgtWkbVidNimK+71Xz8uS2XJbJfbmtO88tYiPujpBg+7qtXptLGuy4\\/hNsvEce9cebHFYrW1t999\\/W9hqq6e3EtD8Kn1I0WHavJNuaeSNo2iYlovxjS5sWnrquFYs18OKuKLzmvWZiI6dIlzRxGmDiWDWaPS49P6GYtGPmteJmJ9s7oPS2w6j0M4stbW0dY3+DToLRSvtjad4nr333fM4TbFgz8Qz6fmz6fFWt\\/RUxxzW3tttHNFtojdy4eKaTT6+Ndi0WSNRFuesW1EzSLefbefvfPpq8+PU21GHLbFltMzNsc8s9e\\/Yg91w+Mfw3DOf0kTbVYbYqxWtZpM4rTy32iO28\\/yeG0WGufV48d9uSZ9bfJFOnj1npDt4fxvPostL3rGea6iNRM3tPNa0VmO\\/2vlmYj1kRqK6a2HDpND8VRba+K2rpM2tP70336W6dP6ODR6ClP0hxWxxyaTBaufJb0tckY6RO872r08P6Pl01s04Zl0XJExky1yc2\\/baJjb+bRXLkpjvSt7VpfbmrE9Lbdt1g9vp6466nU3x4IiufUYM1NRFpn0tbZYmOnaNt9vsfD0N4vxHiehmMkfCueOfHtzU5bTae8xvG0TvDg4bxTLw\\/eK0rkra9LTFpnpy25unvXFxnV6fNkyaaceOb3tbecVLTHN3jeY3SDtj0HD+C6ucGa2rpqpjDzRTlpSY2t2nrM7ez7Xw30J45rpxei5sHo9+bl+DY9t\\/P5rjy55y4sWOaY6xiiaxNaxEz136z4qNYgoKgCiAKIooioAAA9hwz9nYP4IePev4Z14dp\\/4IZ9GPIgKgAAioAAAAAAoAAAAAAAAAAKgCiAKgAAAAAAAAAAAACgAAAAAD6Oi4xl0mCMXJW9Y7bz2fOAFQEUQBUBAAUAAAfe4Tpseg0VOJajHXJmyTMaalusRt3vMePlEIOPT8B1+fDXNbHTBit82+e8Y4n3b9235P5fp+g\\/G\\/s2Z8+XUZJyZslsl57zad2sD5P5fp+g\\/G\\/sfJ\\/L9P0H439gA+T+X6foPxv7Hyfy\\/T9B+N\\/YAPk\\/l+n6D8b+x8n8v0\\/Qfjf2AD5P5fp+g\\/G\\/sfJ\\/L9P0H439gA+T+X6foPxv7Hyfy\\/T9B+N\\/YiJntG5sB8n8v0\\/Qfjf2Pk\\/l+n6D8b+wAfJ\\/L9P0H439j5P5fp+g\\/G\\/sAHyfy\\/T9B+N\\/Y+T+X6foPxv7AKfJ\\/L9P0H439j5P5fp+g\\/G\\/sAHyfy\\/TtB+N\\/Y+T+X6doPxv7AB8n88\\/N1mgtPlGoiP6uLXcO1fD7xXV4LY+b5tu9be6e0u116TWzhrbBnr6fSZOmTDaekx5x5T7YB50d\\/F9B8X6vlpbnwZaxkw3\\/wBVZ7b+2O0+5wKAAAAAAAAAAACIAKAAAAAAD0vFY9HlwYY+bi0+Ktf\\/AMYmf5zMvNPR5pjWcL0mtpPNNKRp80eNbVjaJn312+6U0cYAAAAAAAAAPR\\/o3m02i0uTUan1ZvbliaXmbz2\\/diO0d92viOOtNDxG1K1it8+K1ZrabRO8W67zEOXhPGc2gmaXzZvQRjtWtKT0i0x0n70jis5dFlx622TUZLZcdoi9p2mtebeN\\/Duyrt0Wh0+n0GfBrb1rn1EV9asc0aeI7TaY7bztD4mq02TSZ5xZeXmjrvWYmJjwmJh9HFxfR4sOXDThWOKZoiLx6a\\/Xad48XBq82DNes6fS100RHWIvNt\\/vUc4CgAAAAAACxG87R1kGzi0c\\/AtFktPXHmyY490xWf67\\/e+G+1+kM10+PScPid74KzkzRHhe23T7IiHxTAAUAAAAAAAAABAAAAAAAAB28M4ll4dlvNK1yYskcuXFf5t4\\/wBp9riAeirHDNZbfS62ummf8rVbxt7rx0n7dm34rp9acM\\/NVeYEg9P8V0+tOGfmqnxXT604Z+aq8wEHp\\/iun1pwz81U+K6fWnDPzVXmAg9P8V0+tOGfmqnxXT604Z+aq8wEHp\\/iun1pwz81U+K6fWnDPzVXmAg9P8V0+tOGfmqnxXT604Z+aq8wEHp\\/iun1pwz81U+K6fWnDPzVXmAg9P8AFdPrThn5qp8V0+tOGfmqvMBB6f4rp9acM\\/NVPiun1pwz81V5gIr0\\/wAV0+tOGfmqnxXT604Z+aq8wEHp\\/iun1pwz81U+K6fWnDPzVXmAg9NbhuKkc1+K8NiI\\/wBOoi0\\/dDTbiei4bHNoLTqtX+7mvTlpj9taz1mff9zz4QZXvbJe172m1rTvMzO8zLEFAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAFAAAAAAAAAAAAAAAAAAAAABAAAAAAAABnEepExEd\\/Fgu\\/qxHkgzmK7Tt\\/qheWOaZiOm0\\/ZLXFto29u6xeYm23iKU269t9um6ztO\\/bfl67MaztaJN426RMfaDOvSO0T79mFu6xbaIjrHulLTzTuCAKjKnzvsZ7RtPb7vY1xMx2nZeeduvVBlHzI93sY3\\/d28kiY5dpgmdxWcbbR2226+abfN9zGJjbaY\\/mb9Ynbt5gznrG20R7ejWz5+k95385YLgAA2R\\/hb7Rv7vA\\/fr0\\/d\\/2Yc07779V5\\/Widu0bMTRnMerPTvHTpEMKbbz2326bk2iY7T96VnltE99jM\\/Bn09btvy9dlp0jtE+\\/ZhvXadomPtWL7ViOsbeUk0S8dWK2tzTvtsjWfAAUAAAAAAAAABAAAAAAAAAAAAAAAAAAAAUAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAABQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAGU0tHgkVmd\\/YKgs1mI6ryT7N\\/LcGIMuS3kDEbMeDJlvyUrvMd3fi4VG2+a87+VU3cwfMH3K6DTV\\/y9\\/bMyz+C6f8A9mn3M9D4A+9Oj08\\/5Nfshqvw3T2+bFqe6V6wfGHbqOHZcUTak+kr7O7k5LeS3BiLMTHcmJjbfxUQWY229pyzvHtBBlNLR4Jyztvt0BAAAAAAAAAAAAAAABAAAAAAAAAAAiN5AVs6TNdp7dPeR+9v238mvssWtG+090gytMdvKfJnE9vP2tU2me87nPbzIE9bTs3Y6ekyVpTrO3Ls0O\\/hOPmzWyf6Y2j7Tfg+lhw1w05ax18Z82Yzx475clceOs2vadoiPGXIYN2PSanLG+PBltHnFJl3Xyafhf6vDWmfVR87LaN60nyrHj70w63PqrWnVcUyYNu3zpifuB8\\/JiyYp2yY7UnytGzB96t9ZNJ9Dq8XEsUdbYbbzbb3T1+5w6rTYc2nnV6KJrSs7ZcMzvOOfP3A+e4OIafl2zY426+vER39rvS9YvSaz2mNlz8HwP8AM226xGy\\/uz0\\/\\/bdhbmpeazPWs7JNpmNt3SDKY3rX1ojaPEmI5q7z02YG5BsievnvvMsYnasz137QxjoEABQAAAAAAAAAAAAAEAAAAAAABQAAAAAAAB9fhNdtNafOz5D7PC\\/\\/AEn\\/AHSz6+DrfR4bvp9NqtbHS+OsUxz5Wt03+7d859LB+wNT\\/wBen9Jcxz4tDny6yumtXkyWjmmbeEbb7z9jpjJoceT0Ol0U6uY\\/fvNt7e6sdnbnnbXcStHeNLG0\\/ZWHyMuLJo40+amS0Tlx88TXpt1mNt\\/sB11x4c1bZtBF9NqsEc84uaZ3iO81nv08nRTNWc+l10ViKarfDqKR2me0z9u8S2Ybzk4jwvPf\\/FzY5jJP+rrMbz9jkr04Vi28NbP9IB8\\/U4Z0+py4bd8dpr90tTv43+2dX\\/1JcAPh6+vLrMkR57ud1cS\\/9Zf3R\\/RyuufAAUAAAAAAAAAAAAAAAAABAAAAUAAAAAAAAAAAAfY4VO+lmPK0vjvpcIydcmP\\/ALoZ9fB9J9HST6ThGtwx86s0yxHsjpP9XznRodVOj1NcsVi0dYtWe1qz3hzH1M2opTWYtXeJnS6vDGPJMeE7bT9sTG6Vw67T4YpTTY+IaSJmcd+WbxHu26x7mMxGmwWtSnwrhmad9v3sc\\/7T\\/VojHoes6fiWXDWe9b453++O4OTVarLqsvpMsxvEbRERtFY8oh16rfTcK0uCemW95zzHjET0r\\/yUnh2j2vS19ZmjrEWry0j3+MuLUZ8mpzWzZrc17TvMgxy5L5slsmS02vad5me8ywCekbg+JxCd9bk9m0fyczPNf0ma9\\/8AVMywdsAAAAAAAAAAAAAAAAAAAAQAFAAAAAAAAAAAAAAG3TZpwZ63jtHePY1APR0tF6xas7xPWJV8TSa2+n9X51PLy9z6mLW4Mu214rPlbo5b5g7tNq8+kvz4Mk0me8eE++PF0fGNLzvm0Glvbzis1\\/pLgEHdfieT0dseDDgwVvG0+jp1mPfO8uES96Y43vaKx5zOwK5OI6iMWGaRPr36e6GOfiWOkbYvXt5+D5WTJbLeb3neZa8+f9GIDoAAAAAAAAAAAAAAAAAALtPlJtPlLuGOxw7T5SbT5S7g7HDtPlJtPlLuDscO0+Um0+Uu4Ohw7T5SbT5S7g6HDtPlJtPlLuDocO0+Um0+Uu4Oxw7T5SbT5S7g7HDtPlJtPlLuDscO0+Um0+Uu4Oxw7T5SbT5S7g7HFW16\\/Nm0e5l6XN\\/7mT75dYdDknLmnve8\\/bLCeaZ67y7g6HDtPlJtPlLuDscO0+Um0+Uu4Oxw7T5SbT5S7g7HDtPlJtPlLuDscO0+Um0+Uu4Oxw7T5SbT5S7g7HDtPlJtPlLuDscO0+Um0+Uu4Oxw7T5SbT5S7g7HDtPlJtPlLuDscO0+Um0+Uu4OwAYAAAAAAAAAAAAAAAAAAAAAAAAAHTOjvbTUz4pjLWZ2tFe9Z8pBzDo1WmnSzWl71nJMb2rH7vvc4AAAAANunwX1GauLHG9rdtwah3avJiwYp0mniLbT+syTHW0x5exwgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAN2n1OXTXm+G81mY2lpAWZm0zMzvM95lAAAAAAZUval4tS01tE7xMeDEB26nNh1eD01v1eqjaLREdMnt9kuIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf\\/9k\\\\u003d\\\",\\\"10002_kornm\\\":\\\"woori\\\",\\\"10002_residno\\\":\\\"1234\\\",\\\"10002_address\\\":\\\"1234\\\",\\\"10002_issueddate\\\":\\\"2019.4.4\\\"},\\\"@context\\\":\\\"N\\/A\\\",\\\"id\\\":\\\"6701897627329010026\\\",\\\"issued\\\":\\\"2019-04-04T06:24:43Z\\\",\\\"issuer\\\":\\\"did:woori:issuer_id\\\",\\\"revocation\\\":{\\\"id\\\":\\\"rv_id\\\",\\\"type\\\":\\\"RevocationList\\\"},\\\"signature\\\":{\\\"created\\\":\\\"2019-04-04T06:24:43Z\\\",\\\"creator\\\":\\\"did:woori:issuer_id#key-1\\\",\\\"signatureValue\\\":\\\"50a2dfc161a8fe58a618f2a52775151c84cf8066512f7ee3cea6bfa3fe66cef0ba8439c05a7380f51e472ebc432e8b2a0ef3f418cff5983123617ce4fe56877a\\\",\\\"type\\\":\\\"EcdsaSha256\\\"},\\\"type\\\":[\\\"MobileID\\\",\\\"ProofOfIssuedPlaceCredential\\\",\\\"ProofOfPictureCredential\\\",\\\"ProofOfKorNmCredential\\\",\\\"ProofOfResidNoCredential\\\",\\\"ProofOfAddressCredential\\\",\\\"ProofOfIssuedDateCredential\\\"]}\"]}";
			client.requestProcess(retJsonStr);
		} finally {
			client.shutdown();
		}
	}
}