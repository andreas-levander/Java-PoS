package app.controller;

import app.model.Item;
import app.model.Sale;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CustomerCart.fxml")
public class CustomerCartController {
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
