package app.model;

public class Item {
    private final String name;
    private String price;

    public Item(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
