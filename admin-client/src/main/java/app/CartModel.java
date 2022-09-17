package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;


@Component
public class CartModel {
    private final ObservableList<String> cart;
    private final SimpleStringProperty totalPrice;

    public CartModel() {
        cart = FXCollections.observableArrayList();
        totalPrice = new SimpleStringProperty("0");
    }

    public void addItem(String item, String price) {
        cart.add(item);
        Integer current = Integer.parseInt(totalPrice.get());
        Integer itemPrice = Integer.parseInt(price);
        totalPrice.set( (Integer.toString(current + itemPrice)));
    }

    public void clear() {
        totalPrice.set("0");
        cart.clear();
    }

    public ObservableList<String> getCart() {
        return cart;
    }

    public SimpleStringProperty getTotalPrice() {
        return totalPrice;
    }
}
