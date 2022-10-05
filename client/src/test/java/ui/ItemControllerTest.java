package ui;

import app.controller.cart.CartController;
import app.controller.cart.CashierCartController;
import app.controller.cart.CustomerCartController;
import app.controller.cart.ItemController;
import app.service.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ItemControllerTest {

    AutoCloseable autoCloseable;
    ItemController itemController;
    @Mock
    ItemService itemService;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        itemController = new ItemController(itemService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void searchForItem() {
            var searchStringText = "testing";
            itemController.searchForItem(searchStringText);
            Mockito.verify(itemService).getByName(searchStringText);

            var searchStringNumber = "12345";
            itemController.searchForItem(searchStringNumber);
            Mockito.verify(itemService).getByBarcode(searchStringNumber);

    }
}