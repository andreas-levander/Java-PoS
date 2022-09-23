package app.controller;

import app.model.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class SavedSalesController {
    private final SaleController saleController;
    private final ObservableList<Sale> savedSales;

    public SavedSalesController(SaleController saleController) {
        this.saleController = saleController;
        this.savedSales = FXCollections.observableArrayList();
    }

    public void save(Sale sale) {
        savedSales.add(sale);
    }

    public void remove(int index) {
        savedSales.remove(index);
    }

    public void show(int index) {
        if (index >= 0 && index < savedSales.size()) {
            saleController.showSale(savedSales.get(index));
        }
    }

    public ObservableList<Sale> getSavedSales() {
        return savedSales;
    }
}
