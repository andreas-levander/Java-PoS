package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor @Setter @Getter
@Jacksonized @Builder
public class Item {
    private final String name;
    private final String id;
    private final String barCode;
    private final List<String> keywords;
    private Money price;
    @JsonIgnore
    private Money discount;

    public Item(String name, String id, String barCode, List<String> keywords, Money price) {
        this.name = name;
        this.id = id;
        this.barCode = barCode;
        this.keywords = keywords;
        this.price = price;
    }

    @Override
    public String toString() {
        if (discount != null) {
            return name + " - " + formatMoney().format(price) + "  -" + formatMoney().format(discount);
        }
        return name + " - " + formatMoney().format(price);
    }

    private MonetaryAmountFormat formatMoney() {
        return MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.SYMBOL).build());
    }

    public void addDiscount(Money discount) {
        if (this.discount == null) {
            this.discount = discount;
        } else {
            this.discount = this.discount.add(discount);
        }
    }
}
