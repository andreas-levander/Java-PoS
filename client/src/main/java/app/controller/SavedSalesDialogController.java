package app.controller;

import app.model.Cart;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@FxmlView("/SavedSalesDialog.fxml")
@Component
public class SavedSalesDialogController {
    private final SavedSalesController savedSalesController;

    public SavedSalesDialogController(SavedSalesController savedSalesController) {
        this.savedSalesController = savedSalesController;
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
        stage.setTitle("Saved sales");
        savedSalesListView.setItems(savedSalesController.getSavedSales());
        getSale.setDisable(true);
        removeSale.setDisable(true);

        getSale.setOnAction(actionEvent -> {
            savedSalesController.show(savedSalesListView.getSelectionModel().getSelectedIndex());
            stage.close();
        });

        savedSalesListView.getSelectionModel().selectedItemProperty().addListener((observableValue, sale, t1) -> {
            getSale.setDisable(t1 == null);
            removeSale.setDisable(t1 == null);
        });

        removeSale.setOnAction(actionEvent -> savedSalesController.remove(savedSalesListView.getSelectionModel().getSelectedIndex()));
    }

    public void show() {
        stage.show();
    }
}
