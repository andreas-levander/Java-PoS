package app.controller;

import app.model.CurrentCart;
import app.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CartList.fxml")
public class ListController {
    private final CurrentCart currentCart;

    public ListController(CurrentCart currentCart) {
        this.currentCart = currentCart;
    }

    @FXML
    private ListView<Item> listView;
    @FXML
    private Label totalPrice;

    @FXML
    public void initialize() {
        listView.setItems(currentCart.getCart());
        totalPrice.textProperty().bind(currentCart.getTotalPrice());
    }

}
