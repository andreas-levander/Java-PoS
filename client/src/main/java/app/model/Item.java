package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor @Setter @Getter
@Jacksonized @Builder
public class Item {
    private String name;
    private Double price;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price='" + price.toString() + '\'' +
                '}';
    }
}
