package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.javamoney.moneta.Money;
import java.util.UUID;

@Getter
public class Cart {
    private final ObservableList<Item> items;
    private final UUID id;
    @JsonIgnore
    private final SimpleDoubleProperty observableTotalPrice;
    private Money totalPrice;
    @JsonIgnore
    private final String currency = "EUR";

    public Cart() {
        items = FXCollections.observableArrayList();
        observableTotalPrice = new SimpleDoubleProperty(0.0);
        id = UUID.randomUUID();
        totalPrice = Money.of(0,  currency);
    }

    public void addItem(Item item) {
        items.add(item);
        totalPrice = totalPrice.add(item.getPrice());
        observableTotalPrice.set(totalPrice.getNumber().doubleValue());
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            totalPrice = totalPrice.subtract(items.get(index).getPrice());
            observableTotalPrice.set(totalPrice.getNumber().doubleValue());
            items.remove(index);
        }
    }

    public void recalculateTotalValue(){
        Money newPrice = Money.of(0,  currency);
        for(int i = 0; i < items.size(); i++){
            newPrice = newPrice.add(items.get(i).getPrice());
        }
        totalPrice = newPrice;
        observableTotalPrice.set(totalPrice.getNumber().doubleValue());
    }

    @Override
    public String toString() {
        return "Sale id: " + id.toString();
    }
}
