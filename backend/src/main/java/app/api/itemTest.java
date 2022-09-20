package app.api;

import app.model.Item;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/item")
public class itemTest {

    @GetMapping(path = "/{id}", produces = {"application/json"})
    Item getItem(@PathVariable(name = "id") String id) {
        return new Item(id, Math.random() * 100);
    }
}
