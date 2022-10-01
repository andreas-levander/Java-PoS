package app.controller.cart;

import app.model.Item;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


/** Class responsible for managing the UI element for searching and adding new Items to the currently shown cart */
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
    private Button search;
    @FXML
    private TextField textField;
    @FXML
    private Label notif;
    @FXML
    private ListView<Item> searchList;
    @FXML
    private Button addToCart;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Add Item Dialog");

        search.setOnAction(actionEvent -> {
            try {
                var items = cartController.searchForProduct(textField.getText());
                searchList.getItems().setAll(items);
            } catch (Exception e) {
                // show error in ui ?
                showNotification("product not found", Color.RED);
                throw new RuntimeException(e);
            }
        });
        searchList.getSelectionModel().selectedItemProperty().addListener((observableValue, sale, t1) -> {
            addToCart.setDisable(t1 == null);
        });
        addToCart.setDisable(true);
        addToCart.setOnAction(e -> {
            cartController.addToCart(searchList.getSelectionModel().getSelectedItem());
            showNotification("added product to cart", Color.GREEN);
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
        delay.playFromStart();
    }

}
