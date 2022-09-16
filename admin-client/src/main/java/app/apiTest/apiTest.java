package app.apiTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/hello")
public class apiTest {
    private final helloRpcService rpcService;

    @Autowired
    public apiTest(helloRpcService rpcService) {
        this.rpcService = rpcService;
    }

    @PostMapping
    public String sayHello(@RequestBody String message) {
        Long before = System.currentTimeMillis();
        String response = rpcService.sayHello(message);
        Long after = System.currentTimeMillis();
        Long time = after - before;
        return "gRpc latency: " + time.toString() + "ms" + " response: " + response;
    }
}
