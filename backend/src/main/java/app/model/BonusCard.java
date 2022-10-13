package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor @Getter
@Jacksonized @Builder
public class BonusCard {
    private String number;
    private String goodThruMonth;
    private String goodThruYear;
    private Boolean blocked;
    private Boolean expired;
}
