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
        String resp = paymentService.getResult();
        // TODO maybe use XML parser?
        salesUiController.showCardTransactionResult(new CardTransactionResult(extractXmlValue(resp, "bonusCardNumber"),
                extractXmlValue(resp, "bonusState"), extractXmlValue(resp, "paymentCardNumber"), extractXmlValue(resp, "paymentState"),extractXmlValue(resp, "paymentCardType")));
    }

    // Hacky solution, if anyone wants to take a crack at with an XML Parser
    // feel free :D
    private String extractXmlValue(String ret, String name) {
        int i1 = ret.indexOf("<" + name + ">");
        int i2 = ret.indexOf("</" + name + ">");
        return (i1 > -1 && i2 > -1) ? ret.substring(i1 + name.length() + 2, i2) : "";
    }
}
