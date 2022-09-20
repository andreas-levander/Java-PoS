package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;


@Component
public class CurrentCart {
    private final ObservableList<Item> cart;
    private final SimpleStringProperty totalPrice;

    private Sale currentSale;

    public CurrentCart() {
        cart = FXCollections.observableArrayList();
        totalPrice = new SimpleStringProperty("0");
        currentSale = new Sale();
    }

    public void addItem(String name, Double price) {
        var newItem = new Item(name, price);
        cart.add(newItem);
        currentSale.addItem(newItem);
        Double current = Double.parseDouble(totalPrice.get());
        totalPrice.set((Double.toString(current + price)));
    }

    public void clear() {
        totalPrice.set("0");
        cart.clear();
        currentSale = new Sale();
    }

    public void setCart(Sale sale) {
        this.cart.setAll(sale.getSaleList());
        this.totalPrice.set(sale.getTotalPrice().toString());
        this.currentSale = sale;
    }
    public ObservableList<Item> getCart() {
        return cart;
    }

    public SimpleStringProperty getTotalPrice() {
        return totalPrice;
    }
}
