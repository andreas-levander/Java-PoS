package app.controller.cart;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/** Class responsible for managing the UI dialog for scanning an item */

@Component
@FxmlView("/adminUI/cart/ScanDialog.fxml")
public class ScanDialogController {
    private final CartController cartController;
    private final ItemController itemController;

    @FXML
    private Label scanDialogLabel;
    @FXML
    private AnchorPane scanDialog;
    @FXML
    private TextField scanTextField;
    @FXML
    private Button scanDialogBtn;
    private Stage stage;

    public ScanDialogController(CartController cartController, ItemController itemController) {
        this.cartController = cartController;
        this.itemController = itemController;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(scanDialog));
        stage.setTitle("Scan dialog");

        scanDialogBtn.setOnAction(actionEvent -> {
            var items = itemController.searchForItem(scanTextField.getText());
            if (items.isEmpty()) {
                showNotification("product not found", Color.RED);
                return;
            }
            cartController.addToCart(items.get().get(0));
            stage.close();
        });
    }

    public void show() {
        stage.show();
    }

    private void showNotification(String message, Color color) {
        scanDialogLabel.setTextFill(color);
        scanDialogLabel.setText(message);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> scanDialogLabel.setText(""));
        delay.playFromStart();
    }
}
