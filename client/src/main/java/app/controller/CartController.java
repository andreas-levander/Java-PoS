package app.controller;

import app.model.Cart;
import app.service.ItemService;
import org.springframework.stereotype.Component;

@Component
public class CartController {
    private final CashierCartController cashierCartController;
    private final CustomerCartController customerCartController;
    private final ItemService itemService;
    private Cart currentCart;

    public CartController(CashierCartController cashierCartController, CustomerCartController customerCartController, ItemService itemService) {
        this.cashierCartController = cashierCartController;
        this.customerCartController = customerCartController;
        this.itemService = itemService;
    }

    public void newCart() {
        showCart(new Cart());
    }

    public Cart getCurrentCart() {
        return currentCart;
    }

    public void showCart(Cart cart) {
        currentCart = cart;
        customerCartController.bind(currentCart);
        cashierCartController.bind(currentCart);
    }

    public void removeSelectedItem() {
        currentCart.removeItem(cashierCartController.getListView().getSelectionModel().getSelectedIndex());
    }

    public void addProductToCart(String barCode) throws Exception {
            //var item = itemService.getByBarcode(barCode);
            var item = itemService.getByBarcodeForTest(barCode);
            currentCart.addItem(item);

    }

}
