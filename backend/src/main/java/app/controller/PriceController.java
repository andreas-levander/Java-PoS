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

    public Money getPrice(Integer id) {
        System.out.println("Getting price for id" +id);
        var price = priceService.get(id);
        // if price not set we return price of 0
        if (price.isEmpty()) return Money.of(0.0,"EUR");
        System.out.println(price.get().getPrice());
        return price.get().getPrice();
    }
}
