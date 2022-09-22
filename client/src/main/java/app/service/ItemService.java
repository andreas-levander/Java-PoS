package app.service;

import app.model.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ItemService {
    // network call to get item ?
    public Item getByBarcode(String barCode) throws Exception {
//        WebClient client = WebClient.create();
//
//        WebClient.ResponseSpec responseSpec = client.get()
//                .uri("http://localhost:8081/api/v1/item/" + barCode)
//                .retrieve();
//
//        Item responseBody = responseSpec.bodyToMono(Item.class).block();
//        System.out.println(responseBody);
//        //return responseBody;
        return new Item(barCode, (double) Math.round(((Math.random() * 100) * 1000) / 1000));
    }
}
