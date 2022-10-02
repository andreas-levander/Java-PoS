package app.controller;


import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(smBorderPane));
        stage.setTitle("Sales/Marketing");


    }
    public void show() {
        stage.show();
    }
}
