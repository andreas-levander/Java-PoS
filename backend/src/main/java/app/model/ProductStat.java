package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor @Getter
public class ProductStat {
    private Date date;
    private String name;
    private String barCode;
    private Long sold;

    public ProductStat(String name, String barCode, Long sold) {
        this.name = name;
        this.barCode = barCode;
        this.sold = sold;
    }

    public ProductStat(Date date, Long sold) {
        this.date = date;
        this.sold = sold;
    }

    @Override
    public String toString() {
        return name + " - " + sold.toString();
    }
}
