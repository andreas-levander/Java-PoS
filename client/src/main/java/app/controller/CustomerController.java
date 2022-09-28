package app.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CustomerScreen.fxml")
public class CustomerController {
    private Stage stage;

    @FXML
    private Pane customerVBox;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(customerVBox));
        stage.setTitle("Customer Screen");

    }

    public void show() {
        stage.show();
    }
}
