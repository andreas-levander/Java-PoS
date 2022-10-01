package app;

import javafx.application.Application;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Class responsible for starting the Spring Boot Application */
@SpringBootApplication
public class AdminClientApplication {
    public static void main(String[] args) {
        Application.launch(javaFXclass.class, args);
    }
}
