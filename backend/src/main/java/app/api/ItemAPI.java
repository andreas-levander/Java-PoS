package app.api;

import app.controller.ItemController;
import app.model.Item;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/item")
public class ItemAPI {
    private final ItemController itemController;

    public ItemAPI(ItemController itemController) {
        this.itemController = itemController;
    }

    @GetMapping(path = "/barcode/{id}", produces = {"application/json"})
    public List<Item> getItemByBarcode(@PathVariable(name = "id") String id) {
        return itemController.getItemByBarcode(id);
    }

    @GetMapping(path = "/name/{id}", produces = {"application/json"})
    public List<Item> getItemByName(@PathVariable(name = "id") String id) {
        return itemController.getItemByName(id);
    }

}
