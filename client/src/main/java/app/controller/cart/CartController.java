package app.controller.cart;

import app.model.Cart;
import app.model.Item;
import app.service.ItemService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/** Class responsible for managing the currently shown cart */
@Component
public class CartController {
    private final CashierCartController cashierCartController;
    private final CustomerCartController customerCartController;
    private Cart currentCart;

    public CartController(CashierCartController cashierCartController, CustomerCartController customerCartController) {
        this.cashierCartController = cashierCartController;
        this.customerCartController = customerCartController;
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
        currentCart.removeItem(cashierCartController.getSelectedIndex());
    }

    public void addToCart(Item item) {
        currentCart.addItem(item);
    }

}
