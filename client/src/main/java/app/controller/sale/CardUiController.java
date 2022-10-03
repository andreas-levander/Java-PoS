package app.controller.sale;

import app.model.payment.CardTransactionResult;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@FxmlView("/CardUi.fxml")
public class CardUiController {

    @FXML
    private AnchorPane cardUiAnchorPane;
    @FXML
    private Label cardReaderStatus, bonusCardNumber, bonusState, paymentCardNumber, paymentState, paymentCardType;

    private List<Label> resultLabels;

    @FXML
    public void initialize() {
        resultLabels = new ArrayList<>(List.of(bonusCardNumber, bonusState, paymentCardNumber, paymentState, paymentCardType));
        hideCardTransactionResult();
    }

    public void setCardReaderStatus(String status) {
        cardReaderStatus.setText("Card reader status: " + status);
    }

    public void showCardTransactionResult(CardTransactionResult cardTransactionResult) {
        bonusCardNumber.setText("Bonus card number: " + cardTransactionResult.getBonusCardNumber());
        bonusState.setText("Bonus state: " + cardTransactionResult.getBonusState());
        paymentCardNumber.setText("Payment card number: " + cardTransactionResult.getPaymentCardNumber());
        paymentState.setText("Payment state: " + cardTransactionResult.getPaymentState());
        paymentCardType.setText("Payment card type: " + cardTransactionResult.getPaymentCardType());
        for (var label : resultLabels) {
            label.setVisible(true);
        }
    }

    public void hideCardTransactionResult() {
        for (var label : resultLabels) {
            label.setVisible(false);
        }
    }

    public AnchorPane getCardUiAnchorPane() {
        return cardUiAnchorPane;
    }
}
