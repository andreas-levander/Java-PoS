package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.javamoney.moneta.Money;
import java.util.List;

@AllArgsConstructor @Setter @Getter
@Jacksonized @Builder
public class Item {
    private final String name;
    private final String id;
    private final String barCode;
    private final List<String> keywords;
    private Money price;

    @Override
    public String toString() {
        return name + " - " + price.toString();
    }
}
