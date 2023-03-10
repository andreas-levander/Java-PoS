package app.model.tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/** Database table and model for sold products/items */
@Setter @Getter @NoArgsConstructor
@Entity @Table
public class SoldProduct {
    @Id
    @SequenceGenerator(
            name = "sold_sequence",
            sequenceName = "sold_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sold_sequence"
    )
    private Integer id;
    private Integer productId;
    private Integer bonusCustomerId;
    private String name;
    private String barCode;
    private Date date;

    public SoldProduct(Integer productId, Integer bonusCustomerId, String barCode, String name, Date date) {
        this.productId = productId;
        this.bonusCustomerId = bonusCustomerId;
        this.barCode = barCode;
        this.name = name;
        this.date = date;
    }
}
