package app.service;

import app.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    public Item getByBarcode(String barCode) throws Exception {
        // network call to get item ?
        return new Item(barCode, (double) Math.round(((Math.random() * 100) * 1000) / 1000));
    }
}
