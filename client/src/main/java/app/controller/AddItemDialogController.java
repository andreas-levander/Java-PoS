package app.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;



@FxmlView("/AddItemDialog.fxml")
@Component
public class AddItemDialogController {
    private final CartController cartController;

    public AddItemDialogController(CartController cartController) {
        this.cartController = cartController;
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
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Add Item Dialog");

        addItem.setOnAction(actionEvent -> {
            try {
                cartController.addProductToCart(textField.getText());
                showNotification("added product", Color.GREEN);
            } catch (Exception e) {
                // show error in ui ?
                showNotification("product not found", Color.RED);
                throw new RuntimeException(e);
            }

        });
    }

    public void show() {
        stage.show();
    }

    private void showNotification(String message, Color color) {
        notif.setTextFill(color);
        notif.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> notif.setText(""));
        delay.play();

    }

}
