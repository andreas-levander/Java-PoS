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
import org.springframework.stereotype.Component;
import java.sql.Date;

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
    private Button searchButton;
    @FXML
    private Label notification;

    public void initialize() {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("MOST SOLD", "LEAST SOLD");
        choiceBox.setItems(availableChoices);
        choiceBox.getSelectionModel().selectFirst();
        // sets list not selectable
        marketingList.setMouseTransparent(true);
        marketingList.setFocusTraversable(false);

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
                }
                case "LEAST SOLD" -> {
                    // NYI
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
