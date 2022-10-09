package app.model.payment;

import app.controller.sale.payments.PaymentsUiController;
import app.model.sale.Sale;
import app.model.sale.SaleStatus;
import app.service.PaymentService;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter @Getter
public class CashPayment implements PaymentInterface {
    private Sale sale;
    private final PaymentsUiController paymentsUiController;
    private final PaymentService paymentService;
    private final UUID id;

    public CashPayment(PaymentsUiController paymentsUiController, PaymentService paymentService) {
        this.paymentsUiController = paymentsUiController;
        this.paymentService = paymentService;
        id = UUID.randomUUID();
    }

    @Override
    public void process() {
        paymentsUiController.showCashWindow(sale);
        paymentService.openCashBox();
    }

    @Override
    public void abort() {
        paymentsUiController.reset();
        sale.setSaleStatus(SaleStatus.ABORTED);
    }

    @Override
    public void reset() {

    }
}
