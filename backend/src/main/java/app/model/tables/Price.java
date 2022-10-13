package app.model.tables;


import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;
import javax.persistence.*;

@Setter @Getter
@Entity
@Table
public class Price {
    @Id
    private Integer productId;
    private Money price;
}
