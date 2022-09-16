package app.apiTest;

import net.devh.boot.grpc.client.inject.GrpcClient;
import hello.example.lib.MyServiceGrpc;
import hello.example.lib.HelloRequest;
import hello.example.lib.HelloReply;
import org.springframework.stereotype.Service;

@Service
public class helloRpcService {
    @GrpcClient("grpc-test")
    MyServiceGrpc.MyServiceBlockingStub syncClient;

    public String sayHello(String message) {
        HelloRequest request = HelloRequest.newBuilder().setName(message).build();
        HelloReply reply = syncClient.sayHello(request);
        return reply.toString();
    }

}
