package app.controller;

import app.model.Sale;
import app.service.PaymentService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void payWithCash(String amount) {

    }

    public void payWithCard(String amount) {
        var response = paymentService.waitForPayment(amount);
    }
}
