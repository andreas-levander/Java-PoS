package app.api_gRPC_Test;

import io.grpc.stub.StreamObserver;
import hello.example.lib.HelloReply;
import hello.example.lib.HelloRequest;
import hello.example.lib.MyServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class grpcImpl extends MyServiceGrpc.MyServiceImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello ==> " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}