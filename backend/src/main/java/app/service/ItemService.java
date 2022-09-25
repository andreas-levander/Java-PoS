package app.service;

import app.model.ProductCatalogItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;

@Service
public class ItemService {

    public ProductCatalogItem findProduct(String barCode) throws JsonProcessingException {
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client.get()
                .uri("http://localhost:9003/rest/findByBarCode/" + barCode)
                .accept(MediaType.APPLICATION_XML)
                .retrieve();

        var resp = responseSpec.bodyToMono(String.class).block();
        var xmlmapper = new XmlMapper();
        var test = xmlmapper.readValue(resp, ProductCatalogItem.class);
        System.out.println(test);
        return new ProductCatalogItem("asd","asd","asd","asd","asd", new ArrayList<>());
    }

}
