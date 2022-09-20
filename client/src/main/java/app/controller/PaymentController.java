package app.controller;

import app.model.CurrentCart;
import app.model.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;


@Component
@FxmlView("/PaymentPanel.fxml")
public class PaymentController {

    private final CurrentCart currentCart;
    private final Payment payment;

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
    private Button cardPaymentButton;
    @FXML
    private Button abortButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button statusButton;


    public PaymentController(CurrentCart currentCart, Payment payment) {
        this.currentCart = currentCart;
        this.payment = payment;

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

        cardPaymentButton.setOnAction((actionEvent -> {
            try {
                payment.send(currentCart.getTotalPrice());
                payment.result();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
                currentCart.clear();
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
