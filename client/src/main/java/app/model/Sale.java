package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Sale {
    private Cart cart;
    private SaleStatus saleStatus;
    private String bonusId;

    public Sale(Cart cart, SaleStatus saleStatus) {
        this.cart = cart;
        this.saleStatus = saleStatus;
    }
}
