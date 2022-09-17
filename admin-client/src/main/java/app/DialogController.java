package app;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@FxmlView("/SimpleDialog.fxml")
@Component
public class DialogController {

    private Stage stage;

    @FXML
    private VBox dialog;

    @FXML
    private Button addItem;

    @FXML
    private TextField textField;
    @FXML
    private TextField priceField;

    private ListController listController;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));

        addItem.setOnAction(actionEvent -> listController.addItem(textField.getText(), priceField.getText()));
    }

    public void show(ListController listController) {
        this.listController = listController;
        stage.show();
    }
}
