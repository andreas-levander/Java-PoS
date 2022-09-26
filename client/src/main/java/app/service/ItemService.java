package app.service;

import app.errors.ItemNotFoundException;
import app.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class ItemService {
    private final WebClient webClient;
    @Value("${backend.baseurl}")
    private String backendBaseUrl;

    public ItemService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Item getByBarcode(String barCode) throws Exception {
        return webClient.get()
                .uri(backendBaseUrl +"/api/v1/item/" + barCode)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(Item.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        //return Mono.just("");
                        throw new ItemNotFoundException("Item with barcode: " + barCode +  " not found");
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();
    }

    public Item getByBarcodeForTest(String barCode) {
        return new Item(barCode,"1","123",new ArrayList<>(),(double) Math.round(((Math.random() * 100) * 1000) / 1000));
    }
}
