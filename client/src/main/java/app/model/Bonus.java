package app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter @Setter
@Jacksonized @Builder
public class Bonus {
    private Integer customerId;
    private Long bonusPoints;
}
