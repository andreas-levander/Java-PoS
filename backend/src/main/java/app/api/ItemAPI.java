package app.api;

import app.controller.ItemController;
import app.model.Item;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/item")
public class ItemAPI {
    private final ItemController itemController;

    public ItemAPI(ItemController itemController) {
        this.itemController = itemController;
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    public Item getItem(@PathVariable(name = "id") String id) {
        return itemController.getItem(id);
    }
}
