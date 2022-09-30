package app.controller.cart;

import app.controller.cart.CartController;
import app.model.Cart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class SavedCartsController {
    private final CartController cartController;
    private final ObservableList<Cart> savedCarts;

    public SavedCartsController(CartController cartController) {
        this.cartController = cartController;
        this.savedCarts = FXCollections.observableArrayList();
    }

    public void save(Cart cart) {
        savedCarts.add(cart);
    }

    public void remove(int index) {
        savedCarts.remove(index);
    }

    public void show(int index) {
        if (index >= 0 && index < savedCarts.size()) {
            cartController.showCart(savedCarts.get(index));
        }
    }

    public ObservableList<Cart> getSavedSales() {
        return savedCarts;
    }
}
