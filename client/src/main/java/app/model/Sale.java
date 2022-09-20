package app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public class Sale {
    private final ObservableList<Item> sale;
    private final UUID id;
    private final SimpleDoubleProperty totalPrice;

    public Sale() {
        sale = FXCollections.observableArrayList();
        totalPrice = new SimpleDoubleProperty(0.0);
        id = UUID.randomUUID();
    }

    public void addItem(Item item) {
        sale.add(item);
        totalPrice.set(totalPrice.get() + item.getPrice());
    }

    public void removeItem(Integer index) {
        //not yet implemented
    }

    public UUID getId() {
        return id;
    }

    public ObservableList<Item> getSaleList() {
        return sale;
    }

    public SimpleDoubleProperty getTotalPrice() {
        return totalPrice;
    }
}
