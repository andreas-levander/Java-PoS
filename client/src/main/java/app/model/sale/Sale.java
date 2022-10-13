package app.model.sale;

import app.model.Cart;
import app.model.payment.BonusCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
public class Sale {
    private final Cart cart;
    private final UUID id;
    @JsonIgnore
    private SaleStatus saleStatus;
    private BonusCard bonusCard;
    private final Date date;

    public Sale(Cart cart, SaleStatus saleStatus) {
        this.cart = cart;
        this.saleStatus = saleStatus;
        id = UUID.randomUUID();
        date = new Date();
    }
}
