package app.controller;

import app.model.CurrentCart;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@FxmlView("/SimpleDialog.fxml")
@Component
public class DialogController {
    private final CurrentCart currentCart;

    public DialogController(CurrentCart currentCart) {
        this.currentCart = currentCart;
    }

    private Stage stage;

    @FXML
    private VBox dialog;

    @FXML
    private Button addItem;

    @FXML
    private TextField textField;
    @FXML
    private TextField priceField;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));

        addItem.setOnAction(actionEvent -> {
            currentCart.addItem(textField.getText(), Double.parseDouble(priceField.getText()));


        });
    }

    public void show() {
        stage.show();
    }
}
