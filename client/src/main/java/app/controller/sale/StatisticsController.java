package app.controller.sale;

import app.model.TotalSoldProductStatistic;
import app.model.sale.SaleFinishedEvent;
import app.service.StatisticService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

/** Class responsible saving finished sales and getting stats for marketing ui */
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

    public List<TotalSoldProductStatistic> getTopProducts(LocalDate start, LocalDate end) {
        return statisticService.getMostSoldProducts(start,end);
    }

    public List<TotalSoldProductStatistic> getLeastSoldProducts(LocalDate start, LocalDate end) {
        return statisticService.getLeastSoldProducts(start,end);
    }
}
