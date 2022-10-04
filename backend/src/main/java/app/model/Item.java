package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;
import java.util.List;

@AllArgsConstructor @Getter @Setter
public class Item {
    private final String name;
    private final String id;
    private final String barCode;
    private final List<String> keywords;
    private Money price;
}
