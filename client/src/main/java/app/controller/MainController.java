package app.controller;

import app.model.CurrentCart;
import app.model.Item;
import app.model.Sale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Button;


@Component
@FxmlView("/MainController.fxml")
public class MainController {
    private final FxWeaver fxWeaver;

    private final CurrentCart currentCart;

    @FXML
    private Button openSimpleDialogButton;
    @FXML
    private Button clearButton;

    @FXML
    private Button openCustomerScreen;
    @FXML
    private Button changeCart;

    public MainController(FxWeaver fxWeaver, CurrentCart currentCart) {
        this.fxWeaver = fxWeaver;
        this.currentCart = currentCart;

    }

    @FXML
    public void initialize() {
        openCustomerScreen.setOnAction(actionEvent -> fxWeaver.loadController(CustomerController.class).show());

        openSimpleDialogButton.setOnAction(
                actionEvent -> fxWeaver.loadController(DialogController.class).show()
        );
        //for testing
        var newSale = new Sale();
        newSale.addItem(new Item("testitem", 2.3));
        changeCart.setOnAction(actionEvent -> currentCart.setCart(newSale));

        clearButton.setOnAction((actionEvent -> currentCart.clear()));


    }


}
