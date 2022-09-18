package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CartList.fxml")
public class ListController {
    private final CartModel cartModel;

    public ListController(CartModel cartModel) {
        this.cartModel = cartModel;
    }

    @FXML
    private ListView<Item> listView;
    @FXML
    private Label totalPrice;

    @FXML
    public void initialize() {
        listView.setItems(cartModel.getCart());
        totalPrice.textProperty().bind(cartModel.getTotalPrice());
    }

}
