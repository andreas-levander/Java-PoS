package app.model.payment;

import app.controller.sale.payments.PaymentsUiController;
import app.model.sale.Sale;
import app.model.sale.SaleStatus;
import app.service.PaymentService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Setter @Getter
public class CashPayment implements PaymentInterface {
    private Sale sale;
    private final PaymentsUiController paymentsUiController;
    private final PaymentService paymentService;
    private final UUID id;
    private final Timeline timeline;

    public CashPayment(PaymentsUiController paymentsUiController, PaymentService paymentService) {
        this.paymentsUiController = paymentsUiController;
        this.paymentService = paymentService;
        id = UUID.randomUUID();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateCardReaderStatus()));
        timeline.setCycleCount(1000);
    }

    @Override
    public void process() {
        paymentsUiController.showCashWindow(sale);
        paymentService.openCashBox();
        // to allow bonus card registration
        paymentService.waitForPayment("0");
    }

    @Override
    public void abort() {
        paymentsUiController.reset();
        sale.setSaleStatus(SaleStatus.ABORTED);
    }

    private void updateCardReaderStatus() {
        var resp = paymentService.getStatus();
        System.out.println(resp);
        // register bonus card if bonus card is accepted
        if (Objects.equals(resp, "DONE")) {
            timeline.stop();
            var result = paymentService.getResult();
            if (Objects.equals(result.getBonusState(), "ACCEPTED"))
                sale.setBonusCard(new BonusCard(result.getBonusCardNumber(),result.getGoodThruMonth(),result.getGoodThruYear()));
        }
    }

    @Override
    public void reset() {
        paymentService.resetCardReader();
    }
}
