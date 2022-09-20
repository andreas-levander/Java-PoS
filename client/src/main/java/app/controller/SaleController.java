package app.controller;

import app.model.Sale;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

@Component
public class SaleController {
    private final CashierCartController cashierCartController;
    private final CustomerCartController customerCartController;


    private Sale currentSale;

    private Sale savedSale;

    public SaleController(CashierCartController cashierCartController, CustomerCartController customerCartController) {
        this.cashierCartController = cashierCartController;
        this.customerCartController = customerCartController;
        savedSale = new Sale();
    }

    public void newSale() {
        currentSale = new Sale();
        customerCartController.bind(currentSale);
        cashierCartController.bind(currentSale);
    }


    public Sale getCurrentSale() {
        return currentSale;
    }

    public void setSale(Sale sale) {
        currentSale = sale;
        customerCartController.bind(currentSale);
        cashierCartController.bind(currentSale);
    }

    public Sale getSavedSale() {
        return savedSale;
    }

    public void saveCurrentSale() {
        this.savedSale = currentSale;
    }
}
