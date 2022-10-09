package app.controller.sale;

import app.model.ProductStatistic;
import app.model.sale.SaleFinishedEvent;
import app.service.StatisticService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class StatisticsController {
    private final StatisticService statisticService;

    public StatisticsController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @EventListener
    public void handler(SaleFinishedEvent event) {
        System.out.println("sale finished from stats controller");
        statisticService.saveSale(event.sale());
    }

    public List<ProductStatistic> getTopProducts(Date start, Date end) {
        return List.of(new ProductStatistic("Potato", 200L), new ProductStatistic("Tomato", 150L));
    }
}
