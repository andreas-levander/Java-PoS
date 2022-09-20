package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class WebConfig {

    @Bean
    public ObjectMapper configureJson() {
        return new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .build();
    }
}
