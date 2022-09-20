package app.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sale {
    private final ObservableList<Item> sale;
    private Double totalPrice;

    public Sale() {
        this.sale = FXCollections.observableArrayList();
        this.totalPrice = 0.0;
    }

    public void addItem(Item item) {
        sale.add(item);
        totalPrice += item.getPrice();
    }

    public void removeItem(Integer index) {
        //not yet implemented
    }

    public ObservableList<Item> getSaleList() {
        return sale;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
