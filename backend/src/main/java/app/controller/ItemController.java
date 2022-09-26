package app.controller;

import app.model.Item;
import app.model.ProductCatalogItem;
import app.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {
    private final ItemService itemService;
    private final XmlMapper mapper;

    public ItemController(ItemService itemService) {
        mapper = new XmlMapper();
        this.itemService = itemService;
    }

    public Item getItem(String barCode) {
        String response = itemService.findProduct(barCode);
        System.out.println(response);

        try {
            var productCatalogItem = mapper.readValue(response, ProductCatalogItem.class);
            System.out.println(productCatalogItem);
            // fetch price from database
            return new Item(productCatalogItem.getName(), productCatalogItem.getId(), productCatalogItem.getBarCode(),
                    productCatalogItem.getKeyword(),Math.random() * 100);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
