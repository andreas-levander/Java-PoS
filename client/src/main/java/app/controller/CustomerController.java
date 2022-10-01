package app.controller;

import app.controller.sale.SaleController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/** Class responsible for managing the main customer UI element */
@Component
@FxmlView("/CustomerScreen.fxml")
public class CustomerController {
    private final SaleController saleController;

    public CustomerController(SaleController saleController) {
        this.saleController = saleController;
    }

    private Stage stage;
    @FXML
    private Pane customerVBox;
    @FXML
    private Button cardButton;
    @FXML
    private Button cashButton;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(customerVBox));
        stage.setTitle("Customer Screen");

        cardButton.setDisable(true);
        cashButton.setDisable(true);

        cardButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            saleController.payWithCard();
        });
        cashButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            saleController.payWithCash();
        });
    }

    public void show() {
        stage.show();
    }

    public void toggleSaleButtons() {
        cashButton.setDisable(!cashButton.isDisable());
        cardButton.setDisable(!cardButton.isDisable());
    }
}
