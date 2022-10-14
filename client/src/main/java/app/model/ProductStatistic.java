package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@AllArgsConstructor @Getter
@Jacksonized @Builder
public class ProductStatistic {
    private final Date date;
    private final String name;
    private final String barCode;
    private final Long sold;

    @Override
    public String toString() {
        return name + " - " + sold.toString();
    }
}
