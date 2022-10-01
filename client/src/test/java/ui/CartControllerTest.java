package ui;

import app.controller.cart.CartController;
import app.controller.cart.CashierCartController;
import app.controller.cart.CustomerCartController;
import app.model.Cart;
import app.model.Item;
import app.service.ItemService;
import org.assertj.core.api.Assertions;
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
    @Mock ItemService itemService;
    @Mock CustomerCartController customerCartController;
    @Mock CashierCartController cashierCartController;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        cartController = new CartController(cashierCartController,customerCartController,itemService);
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
        var newItem = new Item("test","1","123", List.of("asd"),1.5);
        cartController.addToCart(newItem);

        Assertions.assertThat(cartController.getCurrentCart().getCart()).contains(newItem);

        cartController.removeSelectedItem();
        Mockito.verify(cashierCartController).getSelectedIndex();

        Assertions.assertThat(cartController.getCurrentCart().getCart()).doesNotContain(newItem);
    }

    @Test
    void searchForProduct() {
        var searchStringText = "testing";
        cartController.searchForProduct(searchStringText);
        Mockito.verify(itemService).getByName(searchStringText);

        var searchStringNumber = "12345";
        cartController.searchForProduct(searchStringNumber);
        Mockito.verify(itemService).getByBarcode(searchStringNumber);
    }

    @Test
    void addToCart() {
        var newItem = new Item("test","1","123", List.of("asd"),1.5);
        cartController.newCart();
        cartController.addToCart(newItem);
        var cart = cartController.getCurrentCart();
        Assertions.assertThat(cart.getCart()).contains(newItem);
    }
}