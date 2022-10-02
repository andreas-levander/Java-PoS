package app.model;

public interface PaymentInterface {
    void process();
    void abort();
    void setSale(Sale sale);
}
