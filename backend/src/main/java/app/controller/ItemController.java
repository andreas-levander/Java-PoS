package app.controller;

import app.model.Item;
import app.service.ItemService;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public List<Item> getItemByBarcode(String barCode) {
        var productCatalogItem = itemService.findProductByBarcode(barCode);
        var list = new ArrayList<Item>();
        list.add(new Item(productCatalogItem.getName(), productCatalogItem.getId(), productCatalogItem.getBarCode(),
                productCatalogItem.getKeyword(), Money.of(Math.random() * 100, "EUR")));

        return list;

    }

    public List<Item> getItemByName(String name) {
        var productCatalogItems = itemService.findProductByName(name).getProducts();
        return productCatalogItems.stream().map(productCatalogItem -> new Item(productCatalogItem.getName(), productCatalogItem.getId(),
                productCatalogItem.getBarCode(), productCatalogItem.getKeyword(), Money.of(Math.random() * 100, "EUR"))).toList();

    }
}
