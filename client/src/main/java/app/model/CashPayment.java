package app.model;

import app.controller.sale.SalesUiController;
import app.service.PaymentService;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter @Getter
public class CashPayment implements PaymentInterface{
    private Sale sale;
    private final SalesUiController salesUiController;
    private final PaymentService paymentService;
    private final UUID id;

    public CashPayment(SalesUiController salesUiController, PaymentService paymentService) {
        this.salesUiController = salesUiController;
        this.paymentService = paymentService;
        id = UUID.randomUUID();
    }

    @Override
    public void process() {
        salesUiController.showCashWindow(sale);
        sale.setSaleStatus(SaleStatus.DONE);
    }

    @Override
    public void abort() {
        salesUiController.reset();
        sale.setSaleStatus(SaleStatus.ABORTED);
    }
}
