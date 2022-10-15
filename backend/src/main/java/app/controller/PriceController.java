package app.controller;

import app.service.PriceService;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;

@Controller
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    /** @return the price for an item
     * @param id of the item */
    public Money getPrice(Integer id) {
        var price = priceService.get(id);
        // if price not set we return price of 0
        if (price.isEmpty()) return Money.of(0.0,"EUR");
        return price.get().getPrice();
    }
}
