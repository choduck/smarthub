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
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Timestamp;
import com.wooribank.smarthub.grpc.Grpc;
import com.wooribank.smarthub.grpc.GrpcServiceGrpc;

import io.grpc.stub.StreamObserver;

/**
 * Created by rayt on 5/16/16.
 */
public class GrpcServiceImpl extends GrpcServiceGrpc.GrpcServiceImplBase {
  // @aiborisov mentioned this needs to be thread safe. It was using non-thread-safe HashSet
  private static Set<StreamObserver<Grpc.GrpcMessageFromServer>> observers = ConcurrentHashMap.newKeySet();
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
        // do something;
      }

      @Override
      public void onCompleted() {
        observers.remove(responseObserver);
      }
    };
  }
}
