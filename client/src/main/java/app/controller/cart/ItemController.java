package app.controller.cart;

import app.model.Item;
import app.service.ItemService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public Optional<List<Item>> searchForItem(String searchString) {
        if(NumberUtils.isParsable(searchString)) {
            // set to local call for testing without needing to run backend
            //return itemService.getByBarcodeForTest(searchString);
            return itemService.getByBarcode(searchString);
        } else {
            // search by name
            //items = itemService.getByBarcodeForTest(searchString);
            return itemService.getByName(searchString);
        }

    }
}
