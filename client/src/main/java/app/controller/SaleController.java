package app.controller;

import app.model.Sale;
import app.service.ItemService;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

@Component
public class SaleController {
    private final CashierCartController cashierCartController;
    private final CustomerCartController customerCartController;
    private final ItemService itemService;
    private Sale currentSale;
    private Sale savedSale;
    public SaleController(CashierCartController cashierCartController, CustomerCartController customerCartController, ItemService itemService) {
        this.cashierCartController = cashierCartController;
        this.customerCartController = customerCartController;
        this.itemService = itemService;
        savedSale = new Sale();
    }

    public void newSale() {
        setSale(new Sale());
    }

    public Sale getCurrentSale() {
        return currentSale;
    }

    private void setSale(Sale sale) {
        currentSale = sale;
        customerCartController.bind(currentSale);
        cashierCartController.bind(currentSale);
    }

    public void removeItem() {
        currentSale.removeItem(cashierCartController.getListView().getSelectionModel().getSelectedIndex());
    }

    public void addProductToSale(String barCode) throws Exception {
        try {
            var item = itemService.getByBarcode(barCode);
            currentSale.addItem(item);
        } catch (Exception e) {
            throw new Exception("item not found or such: " + e);
        }

    }

    public void showSavedSale() {
        setSale(savedSale);
    }

    public void saveCurrentSale() {
        this.savedSale = currentSale;
    }
}
