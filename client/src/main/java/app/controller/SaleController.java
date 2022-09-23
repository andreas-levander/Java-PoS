package app.controller;

import app.model.Sale;
import app.service.ItemService;
import org.springframework.stereotype.Component;

@Component
public class SaleController {
    private final CashierCartController cashierCartController;
    private final CustomerCartController customerCartController;
    private final ItemService itemService;
    private Sale currentSale;

    public SaleController(CashierCartController cashierCartController, CustomerCartController customerCartController, ItemService itemService) {
        this.cashierCartController = cashierCartController;
        this.customerCartController = customerCartController;
        this.itemService = itemService;
    }

    public void newSale() {
        showSale(new Sale());
    }

    public Sale getCurrentSale() {
        return currentSale;
    }

    public void showSale(Sale sale) {
        currentSale = sale;
        customerCartController.bind(currentSale);
        cashierCartController.bind(currentSale);
    }

    public void removeSelectedItem() {
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

}
