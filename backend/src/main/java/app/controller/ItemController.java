package app.controller;

import app.model.Item;
import app.service.ItemService;
import org.springframework.stereotype.Controller;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public Item getItem(String barCode) {
        var productCatalogItem = itemService.findProduct(barCode);

        return new Item(productCatalogItem.getName(), productCatalogItem.getId(), productCatalogItem.getBarCode(),
                    productCatalogItem.getKeyword(),Math.random() * 100);

    }
}
