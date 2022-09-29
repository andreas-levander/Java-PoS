package app.controller;

import app.model.Cart;
import app.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CashierCart.fxml")
public class CashierCartController {

    @FXML
    private ListView<Item> cashierCartListView;
    @FXML
    private Label cashierTotalPrice;

    @FXML
    public void initialize() {
        //TODO maybe disable button depending on selection (if null)
//        listView.getSelectionModel().selectedItemProperty().addListener(((observableValue, item, t1) -> {
//
//        }));
    }

    public void bind(Cart cart) {
        cashierCartListView.setItems(cart.getSaleList());
        cashierTotalPrice.textProperty().bind(cart.getTotalPrice().asString());
    }

    public ListView<Item> getListView() {
        return cashierCartListView;
    }
}
