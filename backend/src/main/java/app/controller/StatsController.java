package app.controller;

import app.model.Sale;
import app.model.tables.SoldProduct;
import app.service.StatsService;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    public void saveSaleStats(Sale sale) {
        var items = sale.getCart().getItems();
        var soldProducts = items.stream().map(item -> new SoldProduct(item.getId(), item.getBarCode(), item.getName(), sale.getDate())).toList();
        statsService.saveSoldProducts(soldProducts);

    }

    public void getTopProducts() {
        var s = new Date(2323223232L);
        var e = new Date();
        statsService.getTopProducts(s, e);
    }
}
