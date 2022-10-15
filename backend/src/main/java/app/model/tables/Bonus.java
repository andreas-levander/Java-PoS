package app.model.tables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

/** Database table for bonus points */
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
@Entity @Table
public class Bonus {
    @Id
    private Integer customerId;
    private Long bonusPoints;


}
