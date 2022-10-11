package app.model;

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

    @Override
    public String toString() {
        return name + " - " + formatMoney().format(price);
    }

    private MonetaryAmountFormat formatMoney() {
        return MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set(CurrencyStyle.SYMBOL).build());
    }

    public void discount(Double percentage){
        this.setPrice(this.getPrice().multiply(percentage));
    }
}
