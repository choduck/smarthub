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



import com.wooribank.smarthub.grpc.Grpc;
import com.wooribank.smarthub.grpc.GrpcServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GrpcClient extends Application {
  private ObservableList<String> messages = FXCollections.observableArrayList();
  private ListView<String> messagesView = new ListView<>();
  private TextField name = new TextField("name");
  private TextField message = new TextField();
  private Button send = new Button();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    messagesView.setItems(messages);

    send.setText("Send");

    BorderPane pane = new BorderPane();
    pane.setLeft(name);
    pane.setCenter(message);
    pane.setRight(send);

    BorderPane root = new BorderPane();
    root.setCenter(messagesView);
    root.setBottom(pane);

    primaryStage.setTitle("gRPC Chat");
    primaryStage.setScene(new Scene(root, 480, 320));

    primaryStage.show();

    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext(true).build();
    GrpcServiceGrpc.GrpcServiceStub grpcService = GrpcServiceGrpc.newStub(channel);
    StreamObserver<Grpc.GrpcMessage> grpc = grpcService.grpc(new StreamObserver<Grpc.GrpcMessageFromServer>() {
      @Override
      public void onNext(Grpc.GrpcMessageFromServer value) {
        Platform.runLater(() -> {
          messages.add(value.getMessage().getFrom() + ": " + value.getMessage().getMessage());
          messagesView.scrollTo(messages.size());
        });
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

    send.setOnAction(e -> {
      
      System.out.println("222222222");	
      grpc.onNext(Grpc.GrpcMessage.newBuilder().setFrom(name.getText()).setMessage(message.getText()).build());
      message.setText("");
    });
    primaryStage.setOnCloseRequest(e -> {grpc.onCompleted(); channel.shutdown(); });
  }
}
