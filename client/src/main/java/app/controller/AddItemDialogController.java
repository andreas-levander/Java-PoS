package app.controller;

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
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
        stage.setTitle("Add Item Dialog");

        addItem.setOnAction(actionEvent -> {
            try {
                saleController.addProductToSale(textField.getText());
            } catch (Exception e) {
                // show error in ui ?
                throw new RuntimeException(e);
            }

        });
    }

    public void show() {
        stage.show();
    }
}
