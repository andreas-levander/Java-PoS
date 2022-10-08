package app.api;


import app.controller.StatsController;
import app.model.Sale;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/top")
    public void getTopProducts() {
        statsController.getTopProducts();
    }
}
