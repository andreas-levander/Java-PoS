package app.controller.sale.payments;

import app.model.sale.Sale;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/** Class responsible for managing the UI element for cash payment Money received dialog */
@FxmlView("/adminUI/payments/CashPaymentDialog.fxml")
@Component
public class CashPaymentReceivedController {

    @FXML
    private TextField amountReceivedField;
    @FXML
    private Button confirmButton;
    @FXML
    private AnchorPane cashDialog;
    @FXML
    private Label totalLabel, errorLabel;

    @FXML
    public void initialize() {
    }

    public void bind(Sale sale) {
        totalLabel.textProperty().bind(sale.getCart().getObservableTotalPrice().asString());
    }

    public AnchorPane getCashDialog() {
        return cashDialog;
    }

    public TextField getAmountReceivedField() {
        return amountReceivedField;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public void showNotification(String message, Color color) {
        errorLabel.setTextFill(color);
        errorLabel.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorLabel.setText(""));
        delay.playFromStart();
    }
}
