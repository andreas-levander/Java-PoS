package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Controller;

@FxmlView("/test.fxml")
@Controller
public class TestController {
    private final MainController mainController;
    private final FxWeaver fxWeaver;

    public TestController(MainController mainController, FxWeaver fxWeaver) {
        this.mainController = mainController;
        this.fxWeaver = fxWeaver;
    }

    private Stage stage;
    @FXML
    private VBox dialog;
    @FXML
    private Button addItem;
    @FXML
    private TextField textField;
    @FXML
    private Label notif;

    @FXML
    public void initialize() {
        var but = new Button("go back");
        but.setOnAction(actionEvent -> fxWeaver.getBean(MainController.class).getBorderPane().setCenter(dialog));
        addItem.setOnAction(actionEvent -> mainController.getBorderPane().setCenter(but));
    }

    public void show() {
    }
}
