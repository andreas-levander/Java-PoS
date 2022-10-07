package app.controller.sale;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class StatisticsController {

    @EventListener
    public void handler(SaleFinishedEvent event) {

        System.out.println("sale finished from stats controller");

    }
}
