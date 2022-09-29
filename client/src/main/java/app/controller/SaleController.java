package app.controller;

import app.model.Cart;
import app.model.Sale;
import app.model.SaleStatus;
import org.springframework.stereotype.Controller;

@Controller
public class SaleController {
    private final PaymentController paymentController;

    public SaleController(PaymentController paymentController) {
        this.paymentController = paymentController;
    }

    private Sale currentSale;

    public void newSale(Cart cart) {
        // create new sale
        currentSale = new Sale(cart, SaleStatus.WAITING_FOR_CUSTOMER);
        // call customer view to let customer choose payment method
        // set buttons interactable
    }

    public void payWithCash(String amount) {
        paymentController.payWithCash(amount);
        currentSale.setSaleStatus(SaleStatus.DONE);

    }

    public void payWithCard(String amount) {
        paymentController.payWithCard(amount);
    }
}
