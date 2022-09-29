package app.controller;

import app.model.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;


@Component
@FxmlView("/PaymentPanel.fxml")
public class PaymentUiController {

    private final CartController cartController;
    private final Payment payment;
    private final FxWeaver fxWeaver;

    // Payment elements
    @FXML
    private Label paymentStatusLabel;
    @FXML
    private Label cardLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private Label bonusCardLabel;
    @FXML
    private Label bonusResultLabel;
    @FXML
    private Label changeLabel;
    @FXML
    private Label cashTotalLabel;
    @FXML
    private Button cardPaymentButton;
    @FXML
    private Button cashPaymentButton;
    @FXML
    private Button splitPaymentButton;
    @FXML
    private Button abortButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button statusButton;


    public PaymentUiController(CartController cartController, Payment payment, FxWeaver fxWeaver) {
        this.cartController = cartController;
        this.payment = payment;
        this.fxWeaver = fxWeaver;

    }

    @FXML
    public void initialize() throws Exception {

        payment.result();

        paymentStatusLabel.textProperty().bind(payment.getPaymentStatus());
        cardLabel.textProperty().bind(payment.getCardNumber());
        resultLabel.textProperty().bind(payment.getPaymentResult());
        typeLabel.textProperty().bind(payment.getCardType());
        bonusCardLabel.textProperty().bind(payment.getBonusNumber());
        bonusResultLabel.textProperty().bind(payment.getBonusResult());
        changeLabel.textProperty().bind(payment.getChange().asString());
        cashTotalLabel.textProperty().bind(payment.getCashTotal().asString());

        cardPaymentButton.setOnAction((actionEvent -> {
            try {
                payment.send(cartController.getCurrentCart().getTotalPrice().get());
                payment.result();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        cashPaymentButton.setOnAction((actionEvent -> {
            payment.setCashTotal(cartController.getCurrentCart().getTotalPrice().get());
            fxWeaver.loadController(CashPaymentDialogController.class).show();
        }));

        splitPaymentButton.setOnAction((actionEvent -> {
            fxWeaver.loadController(SplitPaymentDialogController.class).show();
        }));

        abortButton.setOnAction((actionEvent -> {
            try {
                payment.abort();
                payment.result();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        resetButton.setOnAction((actionEvent -> {
            try {
                payment.reset();
                payment.result();
                cartController.newCart();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        statusButton.setOnAction((actionEvent -> {
            try {
                payment.result();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));


    }

}
