package app.controller.sale;

import app.model.SaleFinishedEvent;
import app.service.StatisticService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

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
}
