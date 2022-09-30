package app.controller;

import app.controller.cart.AddItemDialogController;
import app.controller.cart.CartController;
import app.controller.cart.SavedCartsController;
import app.controller.cart.SavedCartsDialogController;
import app.controller.sale.SaleController;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;


@Component
@FxmlView("/MainController.fxml")
public class MainController {
    private final CartController cartController;
    private final SavedCartsController savedCartsController;
    private final SaleController saleController;

    private final FxWeaver fxWeaver;

    @FXML
    private Button addItemByBarcode;
    @FXML
    private Button clearButton;

    @FXML
    private Button getSavedCart;
    @FXML
    private Button saveCart;
    @FXML
    private Button removeItem;
    @FXML
    private Button checkout;

    public MainController(FxWeaver fxWeaver, CartController cartController, SavedCartsController savedCartsController,
                          SaleController saleController) {
        this.fxWeaver = fxWeaver;
        this.cartController = cartController;
        this.savedCartsController = savedCartsController;
        this.saleController = saleController;

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
            saleController.resetUI();
        }));

        checkout.setOnAction(actionEvent -> {
            saleController.newSale(cartController.getCurrentCart());
            customerController.toggleSaleButtons();
        });

    }

}
