package app.model.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;
import javax.persistence.*;

/** Database table and model for product price */
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table
public class Price {
    @Id
    private Integer productId;
    private Money price;
}
