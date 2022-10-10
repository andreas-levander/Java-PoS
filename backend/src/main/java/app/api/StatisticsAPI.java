package app.api;


import app.controller.StatsController;
import app.model.ProductStat;
import app.model.Sale;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/stats")
public class StatisticsAPI {
    private final StatsController statsController;

    public StatisticsAPI(StatsController statsController) {
        this.statsController = statsController;
    }

    @PostMapping(path = "/save/sale")
    public void saveSale(@RequestBody Sale sale) {
        statsController.saveSaleStats(sale);
    }

    @GetMapping(path = "/mostSold")
    public List<ProductStat> getTopProducts(@RequestParam String start, @RequestParam String end) {
        System.out.println(start + " - " + end);
        var startDate = LocalDate.parse(start);
        var endDate = LocalDate.parse(end);
        return statsController.getTopProducts(startDate, endDate);
    }
}
