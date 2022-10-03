package app.controller.sale;

import app.model.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@FxmlView("/CashPaymentDialog_new.fxml")
@Component
public class CashPaymentReceivedController {

    @FXML
    private TextField amountReceivedField;
    @FXML
    private Button confirmButton;
    @FXML
    private AnchorPane dialog;
    @FXML
    private Label totalLabel;
    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
    }

    public void bind(Sale sale) {
        totalLabel.textProperty().bind(sale.getCart().getTotalPrice().asString());
    }

    public AnchorPane getDialog() {
        return dialog;
    }

    public TextField getAmountReceivedField() {
        return amountReceivedField;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }
}
