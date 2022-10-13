package app.controller;

import app.model.Item;
import app.service.ItemService;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ItemController {
    private final ItemService itemService;
    private final PriceController priceController;

    public ItemController(ItemService itemService, PriceController priceController) {
        this.itemService = itemService;
        this.priceController = priceController;
    }

    public List<Item> getItemByBarcode(String barCode) {
        var productCatalogItem = itemService.findProductByBarcode(barCode);
        return List.of(new Item(productCatalogItem.getName(), productCatalogItem.getId(), productCatalogItem.getBarCode(),
                productCatalogItem.getKeyword(), priceController.getPrice(productCatalogItem.getId())));

    }

    public List<Item> getItemByName(String name) {
        var productCatalogItems = itemService.findProductByName(name).getProducts();
        return productCatalogItems.stream().map(productCatalogItem -> new Item(productCatalogItem.getName(), productCatalogItem.getId(),
                productCatalogItem.getBarCode(), productCatalogItem.getKeyword(), priceController.getPrice(productCatalogItem.getId()))).toList();

    }
}
