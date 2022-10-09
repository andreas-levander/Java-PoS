package app.controller.cart;

import app.model.Cart;
import app.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/** Class responsible for managing the customer cart UI elements */
@Component
@FxmlView("/customerUI/CustomerCart.fxml")
public class CustomerCartController {
    @FXML
    private ListView<Item> customerListView;
    @FXML
    private Label customerTotalPrice;


    @FXML
    public void initialize() {
        // sets list not selectable
        customerListView.setMouseTransparent(true);
        customerListView.setFocusTraversable(false);
    }


    public void bind(Cart cart) {
        customerListView.setItems(cart.getItems());
        customerTotalPrice.textProperty().bind(cart.getObservableTotalPrice().asString());
    }
}
