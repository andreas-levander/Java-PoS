package ui;

import app.controller.cart.CartController;
import app.controller.cart.CashierCartController;
import app.controller.cart.CustomerCartController;
import app.model.Cart;
import app.model.Item;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.List;


class CartControllerTest {
    AutoCloseable autoCloseable;
    CartController cartController;
    @Mock CustomerCartController customerCartController;
    @Mock CashierCartController cashierCartController;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        cartController = new CartController(cashierCartController,customerCartController);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void showCart() {
        var cart = new Cart();
        cartController.showCart(cart);
        Assertions.assertThat(cartController.getCurrentCart()).isEqualTo(cart);
        Mockito.verify(customerCartController).bind(cart);
        Mockito.verify(cashierCartController).bind(cart);
    }

    @Test
    void removeSelectedItem() {
        Mockito.when(cashierCartController.getSelectedIndex()).thenReturn(0);

        cartController.newCart();
        var newItem = new Item("test",1,"123", List.of("asd"),Money.of(2.5, "EUR"));
        cartController.addToCart(newItem);

        Assertions.assertThat(cartController.getCurrentCart().getItems().get(0)).usingRecursiveComparison().isEqualTo(newItem);

        cartController.removeSelectedItem();
        Mockito.verify(cashierCartController).getSelectedIndex();

        Assertions.assertThat(cartController.getCurrentCart().getItems()).isEmpty();
    }


    @Test
    void addToCart() {
        var newItem = new Item("test",1,"123", List.of("asd"), Money.of(2.5, "EUR"));
        cartController.newCart();
        cartController.addToCart(newItem);
        var cart = cartController.getCurrentCart();
        Assertions.assertThat(cart.getItems().get(0)).usingRecursiveComparison().isEqualTo(newItem);
    }
}