package app;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminClientApplication {

    public static void main(String[] args) {
        //SpringApplication.run(javaFXclass.class, args);
        Application.launch(javaFXclass.class, args);
    }
}
