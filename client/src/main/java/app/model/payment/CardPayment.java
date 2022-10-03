package app.model.payment;

import app.controller.sale.SalesUiController;
import app.model.Sale;
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
        paymentService.waitForPayment(String.valueOf(sale.getCart().getTotalPrice().get()));
        timeline.playFromStart();
    }

    @Override
    public void abort() {
        paymentService.abortCardPayment();
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
        var result = paymentService.getResult();
        salesUiController.showCardTransactionResult(result);
    }

    @Override
    public void reset() {
        paymentService.resetCardReader();
    }

}
