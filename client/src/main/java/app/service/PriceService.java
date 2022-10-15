package app.service;

import app.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/** Class responsible for doing network calls for saving prices */
@Service
public class PriceService {
    private final WebClient webClient;
    @Value("${backend.baseurl}")
    private String backendBaseUrl;

    public PriceService(WebClient webClient) {
        this.webClient = webClient;
    }
    // to be used for saving a price to the backend
    public void savePrice(Item item) {
            webClient.post()
                .uri(backendBaseUrl +"/api/v1/price/save")
                .bodyValue(item)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, ClientResponse::createException)
                .bodyToMono(String.class)
                .block();
    }
}
