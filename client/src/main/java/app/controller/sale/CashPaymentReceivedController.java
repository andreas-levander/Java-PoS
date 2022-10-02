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
    private final ApplicationContext applicationContext;

    public CashPaymentReceivedController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    private TextField amountReceivedField;
    @FXML
    private Button cancelButton;
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
        cancelButton.setOnAction(actionEvent ->  applicationContext.getBean(PaymentController.class).abort());
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

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }
}
