package app.controller;

import app.model.Item;
import app.model.Sale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CustomerCart.fxml")
public class CustomerCartController {
    @FXML
    private ListView<Item> customerListView;
    @FXML
    private Label customerTotalPrice;


    @FXML
    public void initialize() {

    }


    public void bind(Sale sale) {
        customerListView.setItems(sale.getSaleList());
        customerTotalPrice.textProperty().bind(sale.getTotalPrice().asString());
    }
}
