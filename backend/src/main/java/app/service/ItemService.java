package app.service;

import app.errors.NoItemInProductCatalog;
import app.model.ProductCatalogItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ItemService {
    @Value("${productCatalog.baseurl}")
    private String baseUrl;
    private final WebClient client;
    private final XmlMapper xmlMapper;

    public ItemService(WebClient client) {
        this.client = client;
        xmlMapper = new XmlMapper();
    }

    public ProductCatalogItem findProduct(String barCode) {
        var response = client.get()
                .uri(baseUrl +"/rest/findByBarCode/" + barCode)
                .accept(MediaType.APPLICATION_XML)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        throw new NoItemInProductCatalog("Item with barcode: " + barCode +  " not found in ProductCatalog");
                    } else {
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                }).block();

        try {
            return xmlMapper.readValue(response, ProductCatalogItem.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
