package app.model.tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private String productId;
    private String name;
    private String barCode;
    private Date date;

    public SoldProduct(String productId, String barCode, String name, Date date) {
        this.productId = productId;
        this.barCode = barCode;
        this.name = name;
        this.date = date;
    }
}
