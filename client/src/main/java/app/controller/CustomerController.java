package app.controller;

import app.controller.sale.CashPaymentDialog_new_Controller;
import app.controller.sale.SaleController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/CustomerScreen.fxml")
public class CustomerController {
    private final SaleController saleController;
    private final FxWeaver fxWeaver;

    public CustomerController(SaleController saleController, FxWeaver fxWeaver) {
        this.saleController = saleController;
        this.fxWeaver = fxWeaver;
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

        cardButton.setOnAction(actionEvent -> saleController.payWithCard());
        cashButton.setOnAction(actionEvent ->
        {
            fxWeaver.loadController(CashPaymentDialog_new_Controller.class).show();
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
