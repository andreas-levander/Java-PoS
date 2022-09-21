package app.controller;

import app.model.Item;
import app.model.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CashierCart.fxml")
public class CashierCartController {

    @FXML
    private ListView<Item> listView;
    @FXML
    private Label totalPrice;

    @FXML
    public void initialize() {
    }

    public void bind(Sale sale) {
        listView.setItems(sale.getSaleList());
        totalPrice.textProperty().bind(sale.getTotalPrice().asString());
    }

}