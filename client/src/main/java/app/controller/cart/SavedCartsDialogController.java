package app.controller.cart;

import app.model.Cart;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


/** Class responsible for managing the saved carts UI elements */
@FxmlView("/SavedCartsDialog.fxml")
@Component
public class SavedCartsDialogController {
    private final SavedCartsController savedCartsController;

    public SavedCartsDialogController(SavedCartsController savedCartsController) {
        this.savedCartsController = savedCartsController;
    }

    private Stage stage;
    @FXML
    private VBox savedSalesVBox;
    @FXML
    private Button getSale;
    @FXML
    private Button removeSale;
    @FXML
    private ListView<Cart> savedSalesListView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(savedSalesVBox));
        stage.setTitle("Saved carts");
        savedSalesListView.setItems(savedCartsController.getSavedCarts());
        getSale.setDisable(true);
        removeSale.setDisable(true);

        getSale.setOnAction(actionEvent -> {
            savedCartsController.show(savedSalesListView.getSelectionModel().getSelectedIndex());
            stage.close();
        });

        savedSalesListView.getSelectionModel().selectedItemProperty().addListener((observableValue, sale, t1) -> {
            getSale.setDisable(t1 == null);
            removeSale.setDisable(t1 == null);
        });

        removeSale.setOnAction(actionEvent -> savedCartsController.remove(savedSalesListView.getSelectionModel().getSelectedIndex()));
    }

    public void show() {
        stage.show();
    }
}
