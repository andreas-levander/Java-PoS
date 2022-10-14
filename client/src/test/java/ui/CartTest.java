package ui;

import app.model.Cart;
import app.model.Item;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private Cart cart;
    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void addItem() {
        var newItem = new Item("test",1337,"123", List.of("test"), Money.of(15.5,"EUR"));

        cart.addItem(newItem);
        Assertions.assertThat(cart.getItems()).isNotEmpty();
        // add item should add a deep copy of the item
        Assertions.assertThat(cart.getItems()).doesNotContain(newItem);
        var deepCopy = cart.getItems().get(0);
        Assertions.assertThat(deepCopy).isNotSameAs(newItem);
        Assertions.assertThat(deepCopy).usingRecursiveComparison().isEqualTo(newItem);

        // total price should update
        Assertions.assertThat(cart.getTotalPrice().getNumber().doubleValue()).isEqualTo(newItem.getPrice().getNumber().doubleValue());

        // total price should take into account discounts
        var discountedItem = new Item("test2", 1330, "123", List.of(), Money.of(20.5, "EUR"),Money.of(5.5, "EUR"));
        cart.addItem(discountedItem);
        var total = newItem.getPrice().add(discountedItem.getPrice().subtract(discountedItem.getDiscount()));
        Assertions.assertThat(cart.getTotalPrice()).usingRecursiveComparison().isEqualTo(total);
    }

    @Test
    void removeItem() {
        var newItem = new Item("test",1337,"123", List.of("test"), Money.of(15.5,"EUR"));
        var discountedItem = new Item("test2", 1330, "123", List.of(), Money.of(20.5, "EUR"),Money.of(5.5, "EUR"));

        cart.addItem(newItem);
        cart.addItem(discountedItem);

        var total = newItem.getPrice().add(discountedItem.getPrice().subtract(discountedItem.getDiscount()));
        Assertions.assertThat(cart.getTotalPrice()).usingRecursiveComparison().isEqualTo(total);

        // removing item removes item
        var item = cart.getItems().get(0);
        cart.removeItem(0);
        Assertions.assertThat(cart.getItems()).doesNotContain(item);

        // total is updated
        Assertions.assertThat(cart.getTotalPrice()).usingRecursiveComparison().isEqualTo(discountedItem.getPrice().subtract(discountedItem.getDiscount()));

        // removing discounted item updates total correctly
        cart.removeItem(0);
        Assertions.assertThat(cart.getTotalPrice()).usingRecursiveComparison().isEqualTo(Money.of(0.0,"EUR"));

    }

    @Test
    void discountItem() {
        var newItem = new Item("test",1337,"123", List.of("test"), Money.of(15.5,"EUR"));
        var discountedItem = new Item("test2", 1330, "123", List.of(), Money.of(20.5, "EUR"),Money.of(5.5, "EUR"));

        cart.addItem(newItem);
        cart.addItem(discountedItem);

        var totalBefore = cart.getTotalPrice();

        cart.discountItem(0,10.0);
        var item = cart.getItems().get(0);
        var expectedDiscount = item.getPrice().multiply(0.1);
        // item discount is updated
        Assertions.assertThat(item.getDiscount()).usingRecursiveComparison().isEqualTo(expectedDiscount);

        var totalAfter = cart.getTotalPrice();
        // total is updated
        Assertions.assertThat(totalAfter).usingRecursiveComparison().isEqualTo(totalBefore.subtract(item.getDiscount()));

        // discounting discounted item updates discount to new value
        cart.discountItem(0, 50.0);
        expectedDiscount = item.getPrice().multiply(0.5);
        Assertions.assertThat(item.getDiscount()).usingRecursiveComparison().isEqualTo(expectedDiscount);

        //total is updated to new discount
        Assertions.assertThat(cart.getTotalPrice()).usingRecursiveComparison().isEqualTo(totalBefore.subtract(item.getDiscount()));


    }
}