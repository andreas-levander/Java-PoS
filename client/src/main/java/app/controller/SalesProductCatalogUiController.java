package app.controller;

import app.controller.cart.ItemController;
import app.model.Item;
import app.service.PriceService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.math.NumberUtils;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;

@Controller
@FxmlView("/adminUI/sales/BrowseProductCatalog.fxml")
public class SalesProductCatalogUiController {
    private final ItemController itemController;
    private final PriceService priceService;

    @FXML
    private Button search, changePrice;
    @FXML
    private TextField searchTextField, setPriceField;
    @FXML
    private Label notif;
    @FXML
    private ListView<Item> searchList;

    public SalesProductCatalogUiController(ItemController itemController, PriceService priceService) {
        this.itemController = itemController;
        this.priceService = priceService;
    }

    @FXML
    public void initialize() {
        changePrice.setDisable(true);
        changePrice.setOnAction(actionEvent -> {
            var item = searchList.getSelectionModel().getSelectedItem();
            var price = setPriceField.getText();
            var oldPrice = item.getPrice();

            if (!NumberUtils.isParsable(price)) {
                showNotification("Please enter price", Color.RED);
            }

            item.setPrice(Money.of(Double.parseDouble(price), "EUR"));
            try {
                priceService.savePrice(item);
            } catch (Exception e) {
                item.setPrice(oldPrice);
                showNotification("Error setting price", Color.RED);
            }
            searchList.refresh();

        });

        searchList.getSelectionModel().selectedItemProperty().addListener((observableValue, item, t1) -> changePrice.setDisable(t1 == null));
        search.setOnAction(actionEvent -> {
            try {
                var items = itemController.searchForItem(searchTextField.getText());
                searchList.getItems().setAll(items);
            } catch (Exception e) {
                // show error in ui ?
                showNotification("product not found", Color.RED);
                throw new RuntimeException(e);
            }
        });
    }

    private void showNotification(String message, Color color) {
        notif.setTextFill(color);
        notif.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> notif.setText(""));
        delay.playFromStart();
    }
}
