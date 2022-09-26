package app.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/hello")
public class apiTest {

    @GetMapping
    public String sayHello() {
        return "hello";
    }
}
