package app.controller;

import app.model.Sale;
import app.service.ItemService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class SaleController {
    private final CashierCartController cashierCartController;
    private final CustomerCartController customerCartController;
    private final ItemService itemService;
    private Sale currentSale;
    private ObservableList<Sale> savedSales;
    public SaleController(CashierCartController cashierCartController, CustomerCartController customerCartController, ItemService itemService) {
        this.cashierCartController = cashierCartController;
        this.customerCartController = customerCartController;
        this.itemService = itemService;
        savedSales = FXCollections.observableArrayList();
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

    public void showSavedSale(int index) {
        if (index >= 0 && index < savedSales.size()) {
            setSale(savedSales.get(index));
        }
    }
    public void removeSavedSale(int index) {
        if (index >= 0 && index < savedSales.size()) {
            savedSales.remove(index);
        }
    }

    public ObservableList<Sale> getSavedSales() {
        return savedSales;
    }

    public void saveCurrentSale() {
        savedSales.add(currentSale);
    }
}
