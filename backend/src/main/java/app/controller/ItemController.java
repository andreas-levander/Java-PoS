package app.controller;

import app.model.Item;
import app.model.ProductCatalogItem;
import app.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public Item getItem(String barCode) throws JsonProcessingException {
        ProductCatalogItem catalogItem = itemService.findProduct(barCode);

        return new Item("asd", 2.0);
    }
}
