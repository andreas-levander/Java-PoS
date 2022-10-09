package app.controller.sale;

import app.controller.sale.payments.PaymentController;
import app.controller.sale.payments.PaymentsUiController;
import app.model.Cart;
import app.model.payment.PaymentInterface;
import app.model.sale.Sale;
import app.model.sale.SaleStatus;
import lombok.Getter;
import org.springframework.stereotype.Controller;

@Controller @Getter
public class SaleController {
    private final PaymentController paymentController;
    private final PaymentsUiController paymentsUiController;

    public SaleController(PaymentController paymentController, PaymentsUiController paymentsUiController) {
        this.paymentController = paymentController;
        this.paymentsUiController = paymentsUiController;
    }

    private Sale currentSale;

    public void newSale(Cart cart) {
        // create new sale
        currentSale = new Sale(cart, SaleStatus.WAITING_FOR_CUSTOMER);
        paymentController.reset();
        paymentsUiController.setStatusLabel("Waiting for customer to select payment");
    }


    public void pay(PaymentInterface payment) {
        payment.setSale(currentSale);
        currentSale.setSaleStatus(SaleStatus.PENDING_PAYMENT);
        paymentController.processPayment(payment);
    }


    public void resetUI() {
        paymentsUiController.reset();
    }
}
