package app.model.payment;

import app.model.sale.Sale;

public interface PaymentInterface {
    void process();
    void abort();
    void setSale(Sale sale);
    void reset();
}
