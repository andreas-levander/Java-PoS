package app.controller.sale;

import app.controller.CashPaymentDialogController;
import app.controller.SplitPaymentDialogController;
import app.controller.cart.CartController;
import app.model.Payment;
import javafx.beans.binding.StringBinding;
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

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label changeLabel;
    @FXML
    private Label cashTotalLabel;



    @FXML
    public void initialize() throws Exception {



    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setCashTotalLabel(StringBinding s) {
        cashTotalLabel.textProperty().bind(s);
    }

    public void setChangeLabel(String s) {
        changeLabel.setText(s);
    }
}
