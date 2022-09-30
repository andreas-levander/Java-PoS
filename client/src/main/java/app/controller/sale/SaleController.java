package app.controller.sale;

import app.model.Cart;
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

        salesUiController.setStatusLabel("Waiting for customer to select payment");

    }

    public void payWithCash(Double amountReceived) {
        paymentController.payWithCash(currentSale, amountReceived);
        currentSale.setSaleStatus(SaleStatus.DONE);

    }

    public void payWithCard() {
        paymentController.payWithCard(currentSale);
    }

    public void resetUI() {
        salesUiController.reset();
    }
}
