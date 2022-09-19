package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;


@Component
public class CartModel {
    private final ObservableList<Item> cart;
    private SimpleStringProperty totalPrice;

    public CartModel() {
        cart = FXCollections.observableArrayList();
        totalPrice = new SimpleStringProperty("0");
    }

    public void addItem(String name, String price) {
        cart.add(new Item(name, price));
        Integer current = Integer.parseInt(totalPrice.get());
        Integer itemPrice = Integer.parseInt(price);
        totalPrice.set( (Integer.toString(current + itemPrice)));
    }

    public void clear() {
        totalPrice.set("0");
        cart.clear();
    }

    public ObservableList<Item> getCart() {
        return cart;
    }

    public SimpleStringProperty getTotalPrice() {
        return totalPrice;
    }
}
