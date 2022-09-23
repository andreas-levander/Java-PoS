package app.controller;

import app.model.Sale;
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
    private final SaleController saleController;

    public SavedSalesDialogController(SaleController saleController) {
        this.saleController = saleController;
    }

    private Stage stage;
    @FXML
    private VBox savedSalesVBox;
    @FXML
    private Button getSale;
    @FXML
    private Button removeSale;
    @FXML
    private ListView<Sale> savedSalesListView;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(savedSalesVBox));
        stage.setTitle("Saved sales");
        savedSalesListView.setItems(saleController.getSavedSales());
        getSale.setDisable(true);
        removeSale.setDisable(true);

        getSale.setOnAction(actionEvent -> {
            saleController.showSavedSale(savedSalesListView.getSelectionModel().getSelectedIndex());
            stage.close();
        });

        savedSalesListView.getSelectionModel().selectedItemProperty().addListener((observableValue, sale, t1) -> {
            getSale.setDisable(t1 == null);
            removeSale.setDisable(t1 == null);
        });

        removeSale.setOnAction(actionEvent -> saleController.removeSavedSale(savedSalesListView.getSelectionModel().getSelectedIndex()));
    }

    public void show() {
        stage.show();
    }
}
