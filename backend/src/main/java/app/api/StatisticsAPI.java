package app.api;

import app.controller.StatsController;
import app.model.ProductStat;
import app.model.Sale;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/** Class responsible for the statistics endpoints */
@RestController
@RequestMapping(path = "api/v1/stats")
public class StatisticsAPI {
    private final StatsController statsController;

    public StatisticsAPI(StatsController statsController) {
        this.statsController = statsController;
    }

    @GetMapping(path = "/barCode/{barCode}")
    public List<ProductStat> getProductStat(@PathVariable(name = "barCode") String barCode) {
        return statsController.getProductStat(barCode);
    }

    @GetMapping(path = "/customer/{id}")
    public List<ProductStat> getCustomerStats(@PathVariable(name = "id") String id) {
        return statsController.getCustomerStat(id);
    }

    @PostMapping(path = "/save/sale")
    public void saveSale(@RequestBody Sale sale) {
        statsController.saveSaleStats(sale);
    }

    @GetMapping(path = "/mostSold")
    public List<ProductStat> getTopProducts(@RequestParam String start, @RequestParam String end) {
        var startDate = LocalDate.parse(start);
        var endDate = LocalDate.parse(end);
        return statsController.getTopProducts(startDate, endDate);
    }

    @GetMapping(path = "/leastSold")
    public List<ProductStat> getLeastSoldProducts(@RequestParam String start, @RequestParam String end) {
        var startDate = LocalDate.parse(start);
        var endDate = LocalDate.parse(end);
        return statsController.getLeastSoldProducts(startDate, endDate);
    }
}
