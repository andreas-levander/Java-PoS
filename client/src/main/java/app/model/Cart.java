package app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
public class Cart {
    private final ObservableList<Item> cart;
    private final UUID id;
    private final SimpleDoubleProperty observableTotalPrice;
    private Money totalPrice;

    public Cart() {
        cart = FXCollections.observableArrayList();
        observableTotalPrice = new SimpleDoubleProperty(0.0);
        id = UUID.randomUUID();
        totalPrice = Money.of(0, "EUR");
    }

    public void addItem(Item item) {
        cart.add(item);
        totalPrice = totalPrice.add(item.getPrice());
        observableTotalPrice.set(totalPrice.getNumber().doubleValue());
    }

    public void removeItem(int index) {
        if (index >= 0 && index < cart.size()) {
            totalPrice = totalPrice.subtract(cart.get(index).getPrice());
            observableTotalPrice.set(totalPrice.getNumber().doubleValue());
            cart.remove(index);
        }
    }

    @Override
    public String toString() {
        return "Sale id: " + id.toString();
    }
}
