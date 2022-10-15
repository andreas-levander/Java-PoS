package app.controller;

import app.model.Item;
import app.service.ItemService;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;

import java.util.List;

/** Class responsible for controlling items */
@Controller
public class ItemController {
    private final ItemService itemService;
    private final PriceController priceController;

    public ItemController(ItemService itemService, PriceController priceController) {
        this.itemService = itemService;
        this.priceController = priceController;
    }

    /** @return a list of the item with
     * @param barCode and injects the price from the database */
    public List<Item> getItemByBarcode(String barCode) {
        var productCatalogItem = itemService.findProductByBarcode(barCode);
        return List.of(new Item(productCatalogItem.getName(), productCatalogItem.getId(), productCatalogItem.getBarCode(),
                productCatalogItem.getKeyword(), priceController.getPrice(productCatalogItem.getId())));

    }
    /** @return a list of the item(s) with
     * @param name and injects the price for each item from the database */
    public List<Item> getItemByName(String name) {
        var productCatalogItems = itemService.findProductByName(name).getProducts();
        return productCatalogItems.stream().map(productCatalogItem -> new Item(productCatalogItem.getName(), productCatalogItem.getId(),
                productCatalogItem.getBarCode(), productCatalogItem.getKeyword(), priceController.getPrice(productCatalogItem.getId()))).toList();

    }
}
