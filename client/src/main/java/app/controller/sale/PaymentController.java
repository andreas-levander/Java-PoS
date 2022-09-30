package app.controller.sale;

import app.model.Payment2;
import app.model.Sale;
import app.service.PaymentService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PaymentController {
    private final PaymentService paymentService;
    private final SalesUiController salesUiController;

    private Payment2 currentPayment;
    private final Timeline timeline;

    public PaymentController(PaymentService paymentService, SalesUiController salesUiController) {
        this.paymentService = paymentService;
        this.salesUiController = salesUiController;
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            updateStatus();
        }));
        timeline.setCycleCount(100);
    }

    public void payWithCash(Sale sale) {
        salesUiController.showCashWindow(sale);
    }

    public void payWithCard(Sale sale) {
        currentPayment = new Payment2(sale.getCart().getTotalPrice(),"Pending");

        salesUiController.showCardWindow(currentPayment);

        //var response = paymentService.waitForPayment(amount);

        timeline.playFromStart();
    }

    public void abort() {
        // TODO call abort enpoint on cardreader
        // paymentservice.abort()

        salesUiController.reset();
        timeline.stop();
    }

    private void updateStatus() {
        var resp = paymentService.getStatus();
        System.out.println(resp);
        if (Objects.equals(resp, "DONE")) {
            timeline.stop();
            // showResultOfCardTransaction() ?
        }
        currentPayment.setStatus(resp);
    }

    private void showResultOfCardTransaction() {
        // TODO get result
        // TODO show result
    }

}
