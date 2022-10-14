package app.controller;


import app.controller.sale.StatisticsController;
import app.model.ProductStatistic;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

/** Class responsible for managing the Marketing UI */

@FxmlView("/adminUI/marketing/MarketingUi.fxml")
@Component
public class MarketingController {
    public MarketingController(StatisticsController statisticsController) {
        this.statisticsController = statisticsController;
    }

    private final StatisticsController statisticsController;
    @FXML
    private BorderPane smBorderPane;
    @FXML
    private ListView<ProductStatistic> marketingList;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private DatePicker startDateBox, endDateBox;
    @FXML
    private Button searchButton, getCustomerStatsBtn;
    @FXML
    private Label notification;
    @FXML
    private TextField customerIdTextBox;

    public void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("MOST SOLD", "LEAST SOLD");
        choiceBox.setItems(availableChoices);
        choiceBox.getSelectionModel().selectFirst();
        // sets list not selectable
        marketingList.setMouseTransparent(true);
        marketingList.setFocusTraversable(false);

        getCustomerStatsBtn.setOnAction(actionEvent -> {
            var idString = customerIdTextBox.getText();
            if (!NumberUtils.isParsable(idString)) {
                showNotification("customer id must be a number", Color.RED);
                return;
            }
            var stats = statisticsController.getCustomerStats(idString);
            if (stats.isEmpty()) showNotification("No purchases found", Color.RED);
            marketingList.getItems().setAll(stats);
        });

        searchButton.setOnAction(actionEvent -> {
            var selected = choiceBox.getSelectionModel().getSelectedItem();
            var startDate = startDateBox.getValue();
            var endDate = endDateBox.getValue();

            if (startDate == null || endDate == null) {
                showNotification("Please select start date and end date", Color.RED);
                return;
            }
            switch (selected) {
                case "MOST SOLD" -> {
                    var mostSold = statisticsController.getTopProducts(startDate, endDate);
                    marketingList.getItems().setAll(mostSold);
                    if (mostSold.isEmpty()) showNotification("No Items sold in that time period", Color.ORANGE);
                }
                case "LEAST SOLD" -> {
                    var leastSold = statisticsController.getLeastSoldProducts(startDate, endDate);
                    marketingList.getItems().setAll(leastSold);
                    if (leastSold.isEmpty()) showNotification("No Items sold in that time period", Color.ORANGE);
                }
            }
        });
    }

    private void showNotification(String message, Color color) {
        notification.setTextFill(color);
        notification.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> notification.setText(""));
        delay.playFromStart();
    }

}
