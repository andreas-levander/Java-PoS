package app.model.payment;

import app.controller.sale.payments.PaymentsUiController;
import app.model.sale.Sale;
import app.model.sale.SaleFinishedEvent;
import app.service.BonusService;
import app.service.PaymentService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;
@Setter @Getter
public class BonusPayment implements PaymentInterface {
    private final PaymentService paymentService;
    private final PaymentsUiController paymentsUiController;
    private final BonusService bonusService;

    private Sale sale;
    private final UUID id;
    private final Timeline timeline;

    public BonusPayment(PaymentsUiController paymentsUiController, PaymentService paymentService, BonusService bonusService) {
        this.paymentService = paymentService;
        this.paymentsUiController = paymentsUiController;
        this.bonusService = bonusService;

        id = UUID.randomUUID();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateStatus()));
        timeline.setCycleCount(1000);
    }

    @Override
    public void process() {
        paymentsUiController.showCardWindow(sale);
        paymentService.waitForPayment("0");
        timeline.playFromStart();
    }

    @Override
    public void abort() {
        paymentService.abortCardPayment();
        paymentsUiController.reset();
        timeline.stop();
    }

    @Override
    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public void reset() {
        paymentService.resetCardReader();
    }

    private void updateStatus() {
        var resp = paymentService.getStatus();
        System.out.println(resp);
        paymentsUiController.setCardReaderStatus(resp);
        if (Objects.equals(resp, "DONE")) {
            timeline.stop();
            showResultOfCardTransaction();
        }
    }

    private void showResultOfCardTransaction() {
        var result = paymentService.getResult();
        paymentsUiController.showCardTransactionResult(result);
            if (Objects.equals(result.getBonusState(), "ACCEPTED")) {
                System.out.println(result.getPaymentCardNumber() + " - " + result.getBonusCardNumber());
                var bonusCard = new BonusCard(result.getBonusCardNumber(), result.getGoodThruMonth(), result.getGoodThruYear());
                sale.setBonusCard(bonusCard);

                var bonus = bonusService.getBonus(bonusCard);
                paymentsUiController.showBonusInfo(bonus, sale);
            }
            //publisher.publishEvent(new SaleFinishedEvent(sale));
        }

    }

