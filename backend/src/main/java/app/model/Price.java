package app.model;


import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;
import javax.persistence.*;

@Setter @Getter
@Entity
@Table
public class Price {
    @Id
    @SequenceGenerator(
            name = "price_sequence",
            sequenceName = "price_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "price_sequence"
    )
    private Integer productId;
    private Money price;
}
