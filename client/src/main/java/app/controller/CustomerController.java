package app.controller;

import app.controller.sale.ReceiptController;
import app.controller.sale.SaleController;
import app.controller.sale.payments.PaymentsUiController;
import app.model.payment.BonusPayment;
import app.model.payment.CardPayment;
import app.model.payment.CashPayment;
import app.service.BonusService;
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
@FxmlView("/customerUI/CustomerScreen.fxml")
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
    private Button cardButton, cashButton, receiptBtn, bonusButton;
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
        bonusButton.setDisable(true);
        paymentLabel.setVisible(false);

        cardButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            var paymentService = applicationContext.getBean(PaymentService.class);
            var salesUiController = applicationContext.getBean(PaymentsUiController.class);
            var cardPayment = new CardPayment(salesUiController, paymentService, applicationContext);
            saleController.pay(cardPayment);
        });
        cashButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            var paymentService = applicationContext.getBean(PaymentService.class);
            var salesUiController = applicationContext.getBean(PaymentsUiController.class);
            var cashPayment = new CashPayment(salesUiController, paymentService);
            saleController.pay(cashPayment);
        });

        bonusButton.setOnAction(actionEvent -> {
            toggleSaleButtons();
            var paymentService = applicationContext.getBean(PaymentService.class);
            var paymentsUiController = applicationContext.getBean(PaymentsUiController.class);
            var bonusService = applicationContext.getBean(BonusService.class);
            var bonusPayment = new BonusPayment(paymentsUiController, paymentService, bonusService);
            saleController.pay(bonusPayment);
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
        bonusButton.setDisable(!bonusButton.isDisable());
        receiptBtn.setDisable(true);
    }
    public void disableSaleButtons() {
        paymentLabel.setVisible(false);
        cashButton.setDisable(true);
        cardButton.setDisable(true);
        receiptBtn.setDisable(true);
        bonusButton.setDisable(true);
    }

    public void toggleReceiptButton() {
        receiptBtn.setDisable(!receiptBtn.isDisable());
    }
}
