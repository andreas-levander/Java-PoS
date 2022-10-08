package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class ProductStat {
    private String name;
    private String barCode;
    private Long sold;

    @Override
    public String toString() {
        return name + " - " + sold.toString();
    }
}
