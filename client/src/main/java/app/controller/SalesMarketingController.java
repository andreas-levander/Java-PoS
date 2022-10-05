package app.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/** Class responsible for managing the Sales/Marketing UI */

@FxmlView("/SaleMarketingUi.fxml")
@Component
public class SalesMarketingController {

    private Stage stage;

    @FXML
    private BorderPane smBorderPane;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button searchButton;

    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(smBorderPane));
        stage.setTitle("Sales/Marketing");

        ObservableList<String> availableChoices = FXCollections.observableArrayList("MOST SOLD", "LEAST SOLD");
        choiceBox.setItems(availableChoices);

        searchButton.setOnAction(actionEvent -> getSelectedChoice());
    }
    public String getSelectedChoice() {
        System.out.println(choiceBox.getSelectionModel().getSelectedItem());
        return choiceBox.getSelectionModel().getSelectedItem();
    }
    public void show() {
        stage.show();
    }
}
