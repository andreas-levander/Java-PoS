package app.controller.sale;

import app.controller.MainController;
import app.model.payment.CardTransactionResult;
import app.model.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
@FxmlView("/SalesUiBase.fxml")
public class SalesUiController {
    private final FxWeaver fxWeaver;
    private final ApplicationContext applicationContext;

    public SalesUiController(FxWeaver fxWeaver, ApplicationContext applicationContext) {
        this.fxWeaver = fxWeaver;
        this.applicationContext = applicationContext;
    }

    @FXML
    private BorderPane pane;
    @FXML
    private Label waitingLabel;
    @FXML
    private Button abort;
    private CardUiController cardUiController;
    private CashUiController cashUiController;

    @FXML
    public void initialize() {
        waitingLabel.setVisible(false);
        cardUiController = fxWeaver.loadController(CardUiController.class);
        cashUiController = fxWeaver.loadController(CashUiController.class);

        abort.setVisible(false);
        abort.setOnAction(e -> {
            applicationContext.getBean(PaymentController.class).abort();
            applicationContext.getBean(MainController.class).toggleCheckoutButton();
        });
    }

    public void setStatusLabel(String s) {
        waitingLabel.setText(s);
        waitingLabel.setVisible(true);
    }

    public void showCardWindow(Sale sale) {
        //cardUiController.getLabel().textProperty().bind(payment.getStatus());
        pane.setCenter(cardUiController.getCardUiAnchorPane());
        abort.setVisible(true);
    }

    public void showCashWindow(Sale sale) {
        cashUiController.bind(sale);
        pane.setCenter(cashUiController.getAnchorPane());
        abort.setVisible(true);
    }

    public void setCardReaderStatus(String status) {
        cardUiController.setCardReaderStatus(status);
    }

    public void showCardTransactionResult(CardTransactionResult cardTransactionResult) {
        cardUiController.showCardTransactionResult(cardTransactionResult);
    }

    public void reset() {
        waitingLabel.setVisible(false);
        abort.setVisible(false);
        cardUiController.setCardReaderStatus("");
        cardUiController.hideCardTransactionResult();
        pane.setCenter(waitingLabel);
    }
}
