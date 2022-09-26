package app.api;

import app.controller.ItemController;
import app.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "api/v1/item")
public class itemTest {
    private final ItemController itemController;

    public itemTest(ItemController itemController) {
        this.itemController = itemController;
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    Item getItem(@PathVariable(name = "id") String id) {
        return itemController.getItem(id);

        //return new Item(id, Math.random() * 100);
    }
}
