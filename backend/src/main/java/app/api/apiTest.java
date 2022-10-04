package app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.jackson.datatype.money.MoneyModule;

@RestController
@RequestMapping(path = "api/v1/hello")
public class apiTest {

    @GetMapping
    public String sayHello() {
        return "hello";
    }
}
