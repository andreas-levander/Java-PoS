package ui;

import app.AdminClientApplication;
import app.controller.cart.CartController;
import app.javaFXclass;
import app.model.Item;
import javafx.stage.Stage;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;


@SpringBootTest(classes = AdminClientApplication.class)
@ExtendWith(ApplicationExtension.class)
class SavedCartsIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Start
    void start(Stage stage) {
        applicationContext.publishEvent(new javaFXclass.StageReadyEvent(stage));
        //add an item to the cart
        applicationContext.getBean(CartController.class).addToCart(new Item("test item", 1, "123", new ArrayList<>(), Money.of(2.0, "EUR")));
    }

    @Test
    void you_can_save_current_Cart(FxRobot robot) {
        //initial cart
        var cart = applicationContext.getBean(CartController.class).getCurrentCart();
        // save cart
        robot.clickOn("#saveCart");

        //make new cart
        robot.clickOn("#clearButton");
        var newCart = applicationContext.getBean(CartController.class).getCurrentCart();

        Assertions.assertThat(newCart).isNotEqualTo(cart);


        robot.clickOn("#getSavedCart");
        // saved carts ui is visible
        var savedCartsUI = robot.lookup("#savedSalesVBox").query();
        Assertions.assertThat(savedCartsUI).isVisible();
        // first item in list is correct cart
        var savedCartsList = robot.lookup("#savedSalesListView").queryListView();
        Assertions.assertThat(savedCartsList).isNotEmpty();
        Assertions.assertThat(savedCartsList.getItems().get(0)).isEqualTo(cart);

        savedCartsList.getSelectionModel().selectFirst();

        robot.clickOn("#getSale");

        // saved cart is loaded
        var currentCart = applicationContext.getBean(CartController.class).getCurrentCart();
        Assertions.assertThat(currentCart).isEqualTo(cart);

    }



}
