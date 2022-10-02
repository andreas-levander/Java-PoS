package app.controller.sale;

import app.model.CardTransactionResult;
import app.model.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@Component
@FxmlView("/SalesUiBase.fxml")
public class SalesUiController {
    private final FxWeaver fxWeaver;

    public SalesUiController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    private Pane pane;
    @FXML
    private Label waitingLabel;
    private CardUiController cardUiController;
    private CashUiController cashUiController;

    @FXML
    public void initialize() {
        waitingLabel.setVisible(false);
        cardUiController = fxWeaver.loadController(CardUiController.class);
        cashUiController = fxWeaver.loadController(CashUiController.class);
    }

    public void setStatusLabel(String s) {
        waitingLabel.setText(s);
        waitingLabel.setVisible(true);
    }

    public void showCardWindow(Sale sale) {
        //cardUiController.getLabel().textProperty().bind(payment.getStatus());
        pane.getChildren().setAll(cardUiController.getCardUiAnchorPane());
    }

    public void showCashWindow(Sale sale) {
        cashUiController.bind(sale);
        pane.getChildren().setAll(cashUiController.getAnchorPane());
    }

    public void setCardReaderStatus(String status) {
        cardUiController.setCardReaderStatus(status);
    }

    public void showCardTransactionResult(CardTransactionResult cardTransactionResult) {
        cardUiController.showCardTransactionResult(cardTransactionResult);
    }

    public void reset() {
        waitingLabel.setVisible(false);
        cardUiController.setCardReaderStatus("");
        cardUiController.hideCardTransactionResult();
        pane.getChildren().setAll(waitingLabel);
    }
}
