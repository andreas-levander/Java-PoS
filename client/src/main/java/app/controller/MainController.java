package app.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;


@Component
@FxmlView("/MainController.fxml")
public class MainController {
    private final CartController cartController;
    private final SavedSalesController savedSalesController;

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
    private BorderPane borderPane;

    public MainController(FxWeaver fxWeaver, CartController cartController, SavedSalesController savedSalesController) {
        this.fxWeaver = fxWeaver;
        this.cartController = cartController;
        this.savedSalesController = savedSalesController;

    }

    @FXML
    public void initialize() {
        addItemByBarcode.setOnAction(
                actionEvent -> fxWeaver.loadController(AddItemDialogController.class).show()
        );
        fxWeaver.loadController(CustomerController.class).show();

        cartController.newCart();

        getSavedCart.setOnAction(actionEvent -> fxWeaver.loadController(SavedSalesDialogController.class).show());
        saveCart.setOnAction(actionEvent -> savedSalesController.save(cartController.getCurrentCart()));

        removeItem.setOnAction(actionEvent -> cartController.removeSelectedItem());

        clearButton.setOnAction((actionEvent -> cartController.newCart()));

        var test = fxWeaver.loadView(TestController.class);
        borderPane.setCenter(test);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
