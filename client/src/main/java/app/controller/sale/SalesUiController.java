package app.controller.sale;

import app.controller.CustomerController;
import app.model.Payment2;
import app.model.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
@FxmlView("/SalesUi.fxml")
public class SalesUiController {
    private final FxWeaver fxWeaver;

    public SalesUiController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    private Pane pane;
    @FXML
    private Label label;
    private CardUiController cardUiController;
    private CashUiController cashUiController;

    @FXML
    public void initialize() {
        label.setVisible(false);
        cardUiController = fxWeaver.loadController(CardUiController.class);
        cashUiController = fxWeaver.loadController(CashUiController.class);

        cashUiController.getCancelButton().setOnAction(e -> reset());
    }

    public void setStatusLabel(String s) {
        label.setText(s);
        label.setVisible(true);
    }

    public void showCardWindow(Payment2 payment) {
        cardUiController.getLabel().textProperty().bind(payment.getStatus());
        pane.getChildren().setAll(cardUiController.getCardUiAnchorPane());
    }

    public void showCashWindow(Sale sale) {
        cashUiController.bind(sale);
        pane.getChildren().setAll(cashUiController.getAnchorPane());
    }

    public void reset() {
        label.setVisible(false);
        pane.getChildren().setAll(label);
    }
}
