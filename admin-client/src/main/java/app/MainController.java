package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import javafx.scene.control.Button;

import java.util.List;

@Component
@FxmlView("/MainController.fxml")
public class MainController {
    private final FxWeaver fxWeaver;
    private ListController listController;

    @FXML
    private Button openSimpleDialogButton;
    @FXML
    private Button clearButton;
    @FXML
    private ListView<String> listView;

    @FXML
    private Label totalPrice;

    public MainController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;

    }

    @FXML
    public void initialize() {
        listController = new ListController(listView, totalPrice);

        openSimpleDialogButton.setOnAction(
                actionEvent -> fxWeaver.loadController(DialogController.class).show(listController)
        );

        clearButton.setOnAction((actionEvent -> listController.clear()));
    }

}
