package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Sale {
    private final Cart cart;
    private final UUID id;
    private SaleStatus saleStatus;
    private String bonusId;

    public Sale(Cart cart, SaleStatus saleStatus) {
        this.cart = cart;
        this.saleStatus = saleStatus;
        id = UUID.randomUUID();
    }
}
