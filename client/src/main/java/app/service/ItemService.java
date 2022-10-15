package app.service;

import app.model.Item;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/** Class responsible for calling the backend for Items */
@Service
public class ItemService {
    private final WebClient webClient;
    @Value("${backend.baseurl}")
    private String backendBaseUrl;

    public ItemService(WebClient webClient) {
        this.webClient = webClient;
    }

    // gets the item with specific bar code from the backend
    public Optional<List<Item>> getByBarcode(String barCode) {
        var response = webClient.get()
                .uri(backendBaseUrl +"/api/v1/item/barcode/" + barCode)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(Item[].class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Mono.empty();
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
        if (response == null) return Optional.empty();
        return Optional.of(Arrays.asList(response));
    }

    // gets the item(s) with specific name from the backend
    public Optional<List<Item>> getByName(String name) {
        var response = webClient.get()
                .uri(backendBaseUrl +"/api/v1/item/name/" + name)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(Item[].class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        return Mono.empty();
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
        if (response == null) return Optional.empty();
        return Optional.of(Arrays.asList(response));
    }

    public Optional<List<Item>> getByBarcodeForTest(String barCode) {
        return Optional.of(new ArrayList<>(List.of(
                new Item(barCode, 1, "123", new ArrayList<>(), Money.of((double) Math.round(((Math.random() * 100) * 1000) / 1000), "EUR")))));
    }
}
