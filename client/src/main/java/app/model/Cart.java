package app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Cart {
    private final ObservableList<Item> cart;
    private final UUID id;
    private final SimpleDoubleProperty totalPrice;

    public Cart() {
        cart = FXCollections.observableArrayList();
        totalPrice = new SimpleDoubleProperty(0.0);
        id = UUID.randomUUID();
    }

    public void addItem(Item item) {
        cart.add(item);
        totalPrice.set(totalPrice.get() + item.getPrice());
    }

    public void removeItem(int index) {
        if (index >= 0 && index < cart.size()) {
            totalPrice.set(totalPrice.get() - cart.get(index).getPrice());
            cart.remove(index);
        }
    }

    @Override
    public String toString() {
        return "Sale id: " + id.toString();
    }
}
