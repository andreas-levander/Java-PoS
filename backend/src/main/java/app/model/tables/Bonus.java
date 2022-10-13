package app.model.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
@Entity @Table
public class Bonus {
    @Id
    @SequenceGenerator(
            name = "bonus_sequence",
            sequenceName = "bonus_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bonus_sequence"
    )
    private Integer customerId;
    private Long bonusPoints;


}
