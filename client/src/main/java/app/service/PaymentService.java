package app.service;

import app.errors.ItemNotFoundException;
import app.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {
    private final WebClient webClient;

    @Value("${cardreader.baseurl}")
    private String cardReaderBaseUrl;

    public PaymentService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String waitForPayment(String amount) {
        return webClient.get()
                .uri(cardReaderBaseUrl +"/c/")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        throw new ItemNotFoundException("Item with barcode: " +  " not found");
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }

    public String getStatus() {
        return webClient.get()
                .uri(cardReaderBaseUrl +"/cardreader/status")
                .accept(MediaType.TEXT_PLAIN)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }
}
