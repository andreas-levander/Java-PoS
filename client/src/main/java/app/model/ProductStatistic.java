package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Jacksonized @Builder
public class ProductStatistic {
    private final String name;
    private final String barCode;
    private final Long sold;

    @Override
    public String toString() {
        return name + " - " + sold.toString();
    }
}
