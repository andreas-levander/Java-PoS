package app.controller.sale;

import app.controller.cart.CartController;
import app.model.Payment;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@FxmlView("/CashPaymentDialog_new.fxml")
@Component
public class CashPaymentDialog_new_Controller {
    private final SaleController saleController;

    public CashPaymentDialog_new_Controller(SaleController saleController) {
        this.saleController = saleController;
    }

    private Stage stage;
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
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Cash Payment");

        totalLabel.textProperty().bind(saleController.getCurrentSale().getCart().getTotalPrice().asString());

        cancelButton.setOnAction((actionEvent -> {
            stage.close();
        }));

        confirmButton.setOnAction((actionEvent -> {
            // TODO Input validation
            var amount = Double.parseDouble(amountReceivedField.getText());
            saleController.payWithCash(amount);
            stage.close();

        }));
    }

    public void show() {
        stage.show();
    }
}
