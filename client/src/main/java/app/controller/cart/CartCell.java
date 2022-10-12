package app.controller.cart;

import app.model.Item;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public class CartCell extends ListCell<Item> {

    // Here we create the reusable layout for each cell. We will use an HBox for the root and labels
    // for info about the item
    final HBox root = new HBox(5);
    final Label sampleNameLabel = new Label();
    final Label discountLabel = new Label();

    // Set the style for the discountlabel in a static block
    {
        discountLabel.setStyle("-fx-text-fill: green; " +
                "-fx-font-style: italic;");

        // Add the sampleNameLabel to the root HBox.
        root.getChildren().add(sampleNameLabel);
        root.getChildren().add(discountLabel);
    }
    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setGraphic(null);

        }
        else {
            sampleNameLabel.setText(item.getName() + " - " + formatMoney().format(item.getPrice()));
            if (item.getDiscount() != null) {
                discountLabel.setText("-" + formatMoney().format(item.getDiscount()));
                discountLabel.setVisible(true);
            } else {
                discountLabel.setVisible(false);
            }

            setGraphic(root);
        }
    }

    private MonetaryAmountFormat formatMoney() {
        return MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.SYMBOL).build());
    }
}
