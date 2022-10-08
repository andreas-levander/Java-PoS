package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
public class Sale {
    private final Cart cart;
    private final UUID id;
    @JsonIgnore
    private SaleStatus saleStatus;
    private String bonusId;
    private final Date date;

    public Sale(Cart cart, SaleStatus saleStatus) {
        this.cart = cart;
        this.saleStatus = saleStatus;
        id = UUID.randomUUID();
        date = new Date();
    }
}
