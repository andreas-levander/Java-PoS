package app.controller;

import app.model.Item;
import app.model.Payment;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@FxmlView("/CashPaymentDialog.fxml")
@Component
public class CashPaymentDialogController {
    private final SaleController saleController;
    private Payment payment;

    public CashPaymentDialogController(SaleController saleController, Payment payment) {
        this.saleController = saleController;
        this.payment = payment;
    }

    private Stage stage;
    private SimpleDoubleProperty change;
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

        totalLabel.textProperty().bind(saleController.getCurrentSale().getTotalPrice().asString());

        cancelButton.setOnAction((actionEvent -> {
            stage.close();
        }));

        confirmButton.setOnAction((actionEvent -> {
            Boolean isValid = payment.calculateChange(amountReceivedField.getText(), saleController.getCurrentSale().getTotalPrice().getValue());
            if(isValid){
                stage.close();
            } else {
                errorLabel.setText("ERROR: INCORRECT INPUT");
            }

        }));
    }

    public void show() {
        stage.show();
    }
}
