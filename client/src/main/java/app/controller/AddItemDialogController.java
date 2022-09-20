package app.controller;

import app.model.Item;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;


@FxmlView("/AddItemDialog.fxml")
@Component
public class AddItemDialogController {
    private final SaleController saleController;

    public AddItemDialogController(SaleController saleController) {
        this.saleController = saleController;
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
        stage.setTitle("Add Item Dialog");

        addItem.setOnAction(actionEvent -> {
            saleController.getCurrentSale().addItem(new Item(textField.getText(), Double.parseDouble(priceField.getText())));

        });
    }

    public void show() {
        stage.show();
    }
}
