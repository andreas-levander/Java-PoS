package app.controller;

import app.controller.cart.ItemController;
import app.model.Item;
import app.model.ProductStatistic;
import app.service.PriceService;
import app.service.StatisticService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.math.NumberUtils;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Controller;

import java.util.Date;

/** Class responsible for managing the SalesMan UI */
@Controller
@FxmlView("/adminUI/sales/SalesUI.fxml")
public class SalesUiController {
    private final ItemController itemController;
    private final PriceService priceService;
    private final StatisticService statsService;

    @FXML
    private Button search, changePrice, getStats;
    @FXML
    private TextField searchTextField, setPriceField;
    @FXML
    private Label notif, tableLabel;
    @FXML
    private ListView<Item> searchList;
    @FXML
    private TableView<ProductStatistic> table;

    public SalesUiController(ItemController itemController, PriceService priceService, StatisticService statsService) {
        this.itemController = itemController;
        this.priceService = priceService;
        this.statsService = statsService;
    }

    @FXML
    public void initialize() {
        changePrice.setDisable(true);
        getStats.setDisable(true);
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
                throw new RuntimeException(e);
            }
            searchList.refresh();

        });

        setupTable();

        getStats.setOnAction(actionEvent -> {
            var selected = searchList.getSelectionModel().getSelectedItem();
            var stats = statsService.getProductStats(selected.getBarCode());
            table.getItems().setAll(stats);
            tableLabel.setText(selected.toString());
        });


        searchList.getSelectionModel().selectedItemProperty().addListener((observableValue, item, t1) -> {
            changePrice.setDisable(t1 == null);
            getStats.setDisable(t1 == null);
        });

        search.setOnAction(actionEvent -> {
                var items = itemController.searchForItem(searchTextField.getText());
                if (items.isEmpty()) {
                    showNotification("product not found", Color.RED);
                    return;
                }
                searchList.getItems().setAll(items.get());

        });
    }

    private void showNotification(String message, Color color) {
        notif.setTextFill(color);
        notif.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> notif.setText(""));
        delay.playFromStart();
    }

    private void setupTable() {
        var column1 = new TableColumn<ProductStatistic, Date>("date");
        var column2 = new TableColumn<ProductStatistic, Long>("sold");
        column1.setCellValueFactory(new PropertyValueFactory<>("date"));
        column2.setCellValueFactory(new PropertyValueFactory<>("sold"));

        column1.setPrefWidth(200);

        table.getColumns().add(column1);
        table.getColumns().add(column2);

    }
}
