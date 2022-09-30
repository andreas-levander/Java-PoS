package app.controller.sale;

import app.model.Payment2;
import app.model.Sale;
import app.service.PaymentService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

@Component
public class PaymentController {
    private final PaymentService paymentService;
    private final SalesUiController salesUiController;

    private Payment2 currentPayment;

    public PaymentController(PaymentService paymentService, SalesUiController salesUiController) {
        this.paymentService = paymentService;
        this.salesUiController = salesUiController;
    }

    public void payWithCash(Sale sale, Double amountReceived) {
        var toGiveBack = amountReceived - sale.getCart().getTotalPrice().get();
        salesUiController.showCashWindow(sale, toGiveBack);
    }

    public void payWithCard(Sale sale) {
        currentPayment = new Payment2(sale.getCart().getTotalPrice(),"Pending");

        salesUiController.bindCardText(currentPayment);
        salesUiController.showCardWindow();

        //var response = paymentService.waitForPayment(amount);

        var timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            // update status label
            var resp = paymentService.getStatus();
            System.out.println(resp);
            currentPayment.setStatus(resp);
        }));
        timeline.setCycleCount(100);
        timeline.play();
    }

    public void abort() {

    }

}
