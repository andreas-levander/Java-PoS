package app.model.payment;

import app.model.Sale;

public interface PaymentInterface {
    void process();
    void abort();
    void setSale(Sale sale);
    void reset();
}
