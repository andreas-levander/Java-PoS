package app;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class ListController {
    private final ListView<String> listView;
    private final Label totalPrice;

    public ListController(ListView<String> listView, Label totalPrice) {
        this.listView = listView;
        this.totalPrice = totalPrice;
    }

    public void addItem(String item, String price) {
        listView.getItems().add(item);
        Integer current = Integer.parseInt(totalPrice.getText());
        Integer itemPrice = Integer.parseInt(price);
        totalPrice.setText(Integer.toString(current + itemPrice));
    }

    public void clear() {
        totalPrice.setText("0");
        listView.getItems().clear();
    }
}
