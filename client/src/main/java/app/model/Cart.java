package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @JsonIgnore
    private final ObjectMapper objectMapper;

    public Cart() {
        items = FXCollections.observableArrayList();
        observableTotalPrice = new SimpleDoubleProperty(0.0);
        id = UUID.randomUUID();
        totalPrice = Money.of(0,  currency);
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    public void addItem(Item item) {
        try {
            Item deepCopy = objectMapper
                    .readValue(objectMapper.writeValueAsString(item), Item.class);
            items.add(deepCopy);
            var price = item.getPrice();
            var discount = item.getDiscount();
            if (discount != null) {
                price = price.subtract(discount);
            }
            totalPrice = totalPrice.add(price);
            observableTotalPrice.set(totalPrice.getNumber().doubleValue());
        } catch (JsonProcessingException e) {
            // should never happen
            throw new RuntimeException(e);
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            var item = items.get(index);
            var price = item.getPrice();
            var discount = item.getDiscount();
            if(discount != null) {
                 price = price.subtract(discount);
            }
            totalPrice = totalPrice.subtract(price);
            observableTotalPrice.set(totalPrice.getNumber().doubleValue());
            items.remove(index);
        }
    }

    public void discountItem(Integer index, Double percent) {
        var item = items.get(index);
        var newPrice = item.getPrice().multiply((100 - percent) / 100);
        var discount = item.getPrice().subtract(newPrice);
        // if we already discounted the item remove old discount
        if (item.getDiscount() != null) {
            var oldDiscount = item.getDiscount();
            totalPrice = totalPrice.add(oldDiscount);
        }
        item.setDiscount(discount);
        totalPrice = totalPrice.subtract(discount);
        observableTotalPrice.set(totalPrice.getNumber().doubleValue());
    }

    @Override
    public String toString() {
        return "Sale id: " + id.toString();
    }
}
