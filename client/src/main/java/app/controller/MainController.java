package app.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/** Class responsible for setting up the main ui */
@Component
@FxmlView("/adminUI/MainController.fxml")
public class MainController {

    @FXML
    private TabPane mainPane;

    private Scene scene;

    @FXML
    public void initialize() {
        this.scene = new Scene(mainPane, 800,600);
    }

    public Scene getScene() {
        return scene;
    }
}
