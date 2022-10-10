package app.controller;

import app.model.ProductStat;
import app.model.Sale;
import app.model.tables.SoldProduct;
import app.service.StatsService;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    public List<ProductStat> getTopProducts(LocalDate start, LocalDate end) {
        return statsService.getTopProducts(start, end);
    }

    public List<ProductStat> getLeastSoldProducts(LocalDate start, LocalDate end) {
        return statsService.getLeastSoldProducts(start, end);
    }
}
