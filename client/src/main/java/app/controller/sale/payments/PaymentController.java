package app.controller.sale.payments;

import app.model.payment.PaymentInterface;
import org.springframework.stereotype.Component;


@Component
public class PaymentController {
    private PaymentInterface currentPayment;

    public void processPayment(PaymentInterface payment) {
        currentPayment = payment;
        payment.process();
    }

    public void abort() {
        currentPayment.abort();
    }

    public void reset() {
        if (currentPayment != null)
            currentPayment.reset();
    }

}
