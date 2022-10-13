package app.model.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class BonusCard {
    private String number;
    private String goodThruMonth;
    private String goodThruYear;
}
