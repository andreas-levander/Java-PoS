package ui;

import app.controller.cart.CartController;
import app.controller.cart.SavedCartsController;
import app.model.Cart;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SavedCartsControllerTest {

    AutoCloseable autoCloseable;
    @Mock CartController cartController;
    SavedCartsController savedCartsController;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        savedCartsController = new SavedCartsController(cartController);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void save() {
        var newCart = new Cart();
        savedCartsController.save(newCart);
        Assertions.assertThat(savedCartsController.getSavedCarts()).contains(newCart);
    }

    @Test
    void remove() {
        var newCart = new Cart();
        savedCartsController.save(newCart);
        Assertions.assertThat(savedCartsController.getSavedCarts()).contains(newCart);

        var newCartIndex = savedCartsController.getSavedCarts().indexOf(newCart);
        savedCartsController.remove(newCartIndex);
        Assertions.assertThat(savedCartsController.getSavedCarts()).doesNotContain(newCart);
    }

    @Test
    void show() {
        var newCart = new Cart();
        savedCartsController.save(newCart);
        Assertions.assertThat(savedCartsController.getSavedCarts()).contains(newCart);
        var newCartIndex = savedCartsController.getSavedCarts().indexOf(newCart);
        savedCartsController.show(newCartIndex);
        Mockito.verify(cartController).showCart(newCart);
    }
}