package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@Jacksonized @Builder
public class Sale {
    private final Cart cart;
    private final UUID id;
    private BonusCard bonusCard;
    private final Date date;
}
