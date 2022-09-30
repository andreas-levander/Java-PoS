package app.controller.sale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CardUi.fxml")
public class CardUiController {
    private final PaymentController paymentController;

    public CardUiController(PaymentController paymentController) {
        this.paymentController = paymentController;
    }

    @FXML
    private AnchorPane cardUiAnchorPane;
    @FXML
    private Label label;
    @FXML
    private Button abort;

    @FXML
    public void initialize() {
        abort.setOnAction(actionEvent -> paymentController.abort());

    }

    public Label getLabel() {
        return label;
    }

    public AnchorPane getCardUiAnchorPane() {
        return cardUiAnchorPane;
    }
}
