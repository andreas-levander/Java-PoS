package app.controller;

import app.controller.cart.*;
import app.controller.sale.SaleController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;

/** Class responsible for managing the main screen (cashier UI) elements */
@Component
@FxmlView("/adminUI/MainController.fxml")
public class MainController {
    private final CartController cartController;
    private final SavedCartsController savedCartsController;
    private final SaleController saleController;
    private final FxWeaver fxWeaver;
    private final CashierCartController cashierCartController;

    @FXML
    private Button addItemByBarcode, clearButton, getSavedCart, saveCart, removeItem, checkout, discountButton, multiplyBtn;
    @FXML
    private Label error;
    @FXML
    private TextField multiplyTextField;

    public MainController(FxWeaver fxWeaver, CartController cartController, SavedCartsController savedCartsController,
                          SaleController saleController, CashierCartController cashierCartController) {
        this.fxWeaver = fxWeaver;
        this.cartController = cartController;
        this.savedCartsController = savedCartsController;
        this.saleController = saleController;
        this.cashierCartController = cashierCartController;
    }

    @FXML
    public void initialize() {
        addItemByBarcode.setOnAction(
                actionEvent -> fxWeaver.loadController(AddItemDialogController.class).show()
        );
        var customerController = fxWeaver.loadController(CustomerController.class);
        customerController.show();

        cartController.newCart();

        getSavedCart.setOnAction(actionEvent -> fxWeaver.loadController(SavedCartsDialogController.class).show());
        saveCart.setOnAction(actionEvent -> savedCartsController.save(cartController.getCurrentCart()));

        removeItem.setOnAction(actionEvent -> cartController.removeSelectedItem());

        clearButton.setOnAction((actionEvent -> {
            cartController.newCart();
            if (checkout.isDisable()) toggleCheckoutButton();
            saleController.resetUI();

        }));

        checkout.setOnAction(actionEvent -> {
            saleController.newSale(cartController.getCurrentCart());
            toggleCheckoutButton();
            customerController.toggleSaleButtons();
        });

        discountButton.setOnAction(actionEvent -> fxWeaver.loadController(DiscountDialogController.class).show());

        multiplyBtn.setOnAction(actionEvent -> {
            var times = multiplyTextField.getText();
            if (!NumberUtils.isParsable(times)) {
                showError("Please enter a number", Color.RED);
                return;
            }
            if (cashierCartController.getSelectedItem() == null) {
                showError("Please select item", Color.RED);
                return;
            }
            cartController.addMultipleOfSelectedItem(Integer.parseInt(times));
        });

    }

    private void toggleAddRemoveButtons() {
        addItemByBarcode.setDisable(!addItemByBarcode.isDisable());
        removeItem.setDisable(!removeItem.isDisable());
    }

    private void toggleSaveCartButtons() {
        saveCart.setDisable(!saveCart.isDisable());
        getSavedCart.setDisable(!getSavedCart.isDisable());
    }

    private void toggleDiscountButton() {
        discountButton.setDisable(!discountButton.isDisable());
    }

    public void toggleCheckoutButton() {
        toggleAddRemoveButtons();
        toggleSaveCartButtons();
        toggleDiscountButton();
        checkout.setDisable(!checkout.isDisable());
    }

    private void showError(String message, Color color) {
        error.setTextFill(color);
        error.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> error.setText(""));
        delay.playFromStart();
    }

}
