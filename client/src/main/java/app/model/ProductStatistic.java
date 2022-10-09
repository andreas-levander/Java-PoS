package app.model;

public class ProductStatistic {
    private final String name;
    private final Long sold;

    public ProductStatistic(String name, Long sold) {
        this.name = name;
        this.sold = sold;
    }

    @Override
    public String toString() {
        return name + " - " + sold.toString();
    }
}
