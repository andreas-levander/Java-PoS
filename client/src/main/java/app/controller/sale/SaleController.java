package app.controller.sale;

import app.model.Cart;
import app.model.payment.PaymentInterface;
import app.model.Sale;
import app.model.SaleStatus;
import lombok.Getter;
import org.springframework.stereotype.Controller;

@Controller @Getter
public class SaleController {
    private final PaymentController paymentController;
    private final SalesUiController salesUiController;

    public SaleController(PaymentController paymentController, SalesUiController salesUiController) {
        this.paymentController = paymentController;
        this.salesUiController = salesUiController;
    }

    private Sale currentSale;

    public void newSale(Cart cart) {
        // create new sale
        currentSale = new Sale(cart, SaleStatus.WAITING_FOR_CUSTOMER);
        paymentController.reset();
        salesUiController.setStatusLabel("Waiting for customer to select payment");
    }


    public void pay(PaymentInterface payment) {
        payment.setSale(currentSale);
        currentSale.setSaleStatus(SaleStatus.PENDING_PAYMENT);
        paymentController.processPayment(payment);
    }


    public void resetUI() {
        salesUiController.reset();
    }
}
