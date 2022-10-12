package app.controller.cart;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

/** Class responsible for managing the discount dialog UI */
@FxmlView("/adminUI/cart/DiscountDialog.fxml")
@Component
public class DiscountDialogController {
    private final CartController cartController;
    private final ItemController itemController;
    private final CashierCartController cashierCartController;

    public DiscountDialogController(CartController cartController, ItemController itemController, CashierCartController cashierCartController) {
        this.cartController = cartController;
        this.itemController = itemController;
        this.cashierCartController = cashierCartController;
    }

    private Stage stage;
    @FXML
    private AnchorPane dialog;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField discountField;
    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Apply discount");

        confirmButton.setOnAction(actionEvent -> {
            var selectedIndex = cashierCartController.getListView().getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                showNotification("Please select an item", Color.RED);
                return;
            }
            var discountText = discountField.getText();
            if (!NumberUtils.isParsable(discountText)) {
                showNotification("Value not a number", Color.RED);
                return;
            }
            var percent = Double.parseDouble(discountText);
            if (percent > 100 || percent < 0) {
                showNotification("Value can not be less than zero or larger than 100", Color.RED);
                return;
            }
            cartController.discountItem(selectedIndex, percent);
            stage.close();
        });

    }

    public void show() {
        stage.show();
    }

    private void showNotification(String message, Color color) {
        errorLabel.setTextFill(color);
        errorLabel.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> errorLabel.setText(""));
        delay.playFromStart();
    }

}
