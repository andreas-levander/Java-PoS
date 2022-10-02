package app.model;

import app.controller.sale.SalesUiController;
import app.service.PaymentService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Setter @Getter
public class CardPayment implements PaymentInterface {
    private Sale sale;
    private final SalesUiController salesUiController;
    private final PaymentService paymentService;
    private final Timeline timeline;
    private final UUID id;

    public CardPayment(SalesUiController salesUiController, PaymentService paymentService) {
        this.salesUiController = salesUiController;
        this.paymentService = paymentService;

        id = UUID.randomUUID();

        timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            updateStatus();
        }));
        timeline.setCycleCount(100);
    }

    @Override
    public void process() {
        salesUiController.showCardWindow(sale);

        //var response = paymentService.waitForPayment(amount);

        timeline.playFromStart();
    }

    @Override
    public void abort() {
        // TODO call abort enpoint on cardreader
        // paymentservice.abort()

        salesUiController.reset();
        timeline.stop();
    }

    private void updateStatus() {
        var resp = paymentService.getStatus();
        System.out.println(resp);
        salesUiController.setCardReaderStatus(resp);
        if (Objects.equals(resp, "DONE")) {
            timeline.stop();
            showResultOfCardTransaction();
        }
    }

    private void showResultOfCardTransaction() {
        // TODO get result
        // TODO show result
        salesUiController.showCardTransactionResult(new CardTransactionResult("12345",
                "ACCEPTED", "45678", "ACCEPTED","DEBIT"));
    }
}
