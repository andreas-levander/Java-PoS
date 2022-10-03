package app.controller.sale;

import app.model.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CashPaymentUi.fxml")
public class CashUiController {
    private final FxWeaver fxWeaver;

    public CashUiController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;

    }

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label changeLabel;
    @FXML
    private Label cashTotalLabel;
    @FXML
    private AnchorPane changePane;

    private CashPaymentReceivedController cashPaymentDialog;
    private double total;
    @FXML
    public void initialize() {
        cashPaymentDialog = fxWeaver.loadController(CashPaymentReceivedController.class);
        anchorPane.getChildren().setAll(cashPaymentDialog.getDialog());

        cashPaymentDialog.getConfirmButton().setOnAction(e ->{
            var amountReceived = cashPaymentDialog.getAmountReceivedField().textProperty().get();
            // TODO input validation
            var x = Double.parseDouble(amountReceived);
            var change = x - total;
            changeLabel.setText(String.valueOf(change));
            showChangePane();
        });
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }


    public void bind(Sale sale) {
        total = sale.getCart().getTotalPrice().get();
        cashTotalLabel.textProperty().bind(sale.getCart().getTotalPrice().asString());
        cashPaymentDialog.bind(sale);
    }

    private void showChangePane() {
        anchorPane.getChildren().setAll(changePane);
    }
}

