package app.controller.cart;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
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
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Apply discount");

        confirmButton.setOnAction(actionEvent -> {
            var item = cashierCartController.getListView().getSelectionModel().getSelectedItem();
            item.discount((100 - Integer.parseInt(discountField.getText())) / 100.0);
            cartController.removeSelectedItem();
            cartController.addToCart(item);
            cartController.recalculateTotalValue();
            stage.close();
        });

    }

    public void show() {
        stage.show();
    }


}
