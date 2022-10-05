package app.controller;

import app.controller.sale.SaleController;
import app.controller.sale.SalesUiController;
import app.model.payment.CardPayment;
import app.model.payment.CashPayment;
import app.service.PaymentService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/** Class responsible for managing the main customer UI element */
@Component
@FxmlView("/CustomerScreen.fxml")
public class CustomerController {
    private final SaleController saleController;
    private final ApplicationContext applicationContext;

    public CustomerController(SaleController saleController, ApplicationContext applicationContext) {
        this.saleController = saleController;
        this.applicationContext = applicationContext;
    }

    private Stage stage;
    @FXML
    private BorderPane customerPane;
    @FXML
    private Button cardButton, cashButton;
    @FXML
    private Label paymentLabel;
    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(customerPane));
        stage.setTitle("Customer Screen");

        cardButton.setDisable(true);
        cashButton.setDisable(true);
        paymentLabel.setVisible(false);

        cardButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            var paymentService = applicationContext.getBean(PaymentService.class);
            var salesUiController = applicationContext.getBean(SalesUiController.class);
            var cardPayment = new CardPayment(salesUiController, paymentService);
            saleController.pay(cardPayment);
        });
        cashButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            var paymentService = applicationContext.getBean(PaymentService.class);
            var salesUiController = applicationContext.getBean(SalesUiController.class);
            var cashPayment = new CashPayment(salesUiController, paymentService);
            saleController.pay(cashPayment);
        });
    }

    public void show() {
        stage.show();
    }

    public void toggleSaleButtons() {
        paymentLabel.setVisible(!paymentLabel.isVisible());
        cashButton.setDisable(!cashButton.isDisable());
        cardButton.setDisable(!cardButton.isDisable());
    }
}
