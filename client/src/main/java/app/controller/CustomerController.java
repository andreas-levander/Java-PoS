package app.controller;

import app.controller.sale.ReceiptController;
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
    private final ReceiptController receiptController;

    public CustomerController(SaleController saleController, ApplicationContext applicationContext, ReceiptController receiptController) {
        this.saleController = saleController;
        this.applicationContext = applicationContext;
        this.receiptController = receiptController;
    }

    private Stage stage;
    @FXML
    private BorderPane customerPane;
    @FXML
    private Button cardButton, cashButton, receiptBtn;
    @FXML
    private Label paymentLabel;
    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(customerPane));
        stage.setTitle("Customer Screen");

        cardButton.setDisable(true);
        cashButton.setDisable(true);
        receiptBtn.setDisable(true);
        paymentLabel.setVisible(false);

        cardButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            var paymentService = applicationContext.getBean(PaymentService.class);
            var salesUiController = applicationContext.getBean(SalesUiController.class);
            var cardPayment = new CardPayment(salesUiController, paymentService, applicationContext);
            saleController.pay(cardPayment);
        });
        cashButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            var paymentService = applicationContext.getBean(PaymentService.class);
            var salesUiController = applicationContext.getBean(SalesUiController.class);
            var cashPayment = new CashPayment(salesUiController, paymentService);
            saleController.pay(cashPayment);
        });

        receiptBtn.setOnAction(actionEvent -> {
            receiptController.printReceipt();
            receiptBtn.setDisable(true);
        });
    }

    public void show() {
        stage.show();
    }

    public void toggleSaleButtons() {
        paymentLabel.setVisible(!paymentLabel.isVisible());
        cashButton.setDisable(!cashButton.isDisable());
        cardButton.setDisable(!cardButton.isDisable());
        receiptBtn.setDisable(true);
    }
    public void disableSaleButtons() {
        paymentLabel.setVisible(false);
        cashButton.setDisable(true);
        cardButton.setDisable(true);
        receiptBtn.setDisable(true);
    }

    public void toggleReceiptButton() {
        receiptBtn.setDisable(!receiptBtn.isDisable());
    }
}
