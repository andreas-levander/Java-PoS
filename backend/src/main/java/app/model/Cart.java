package app.model;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.javamoney.moneta.Money;

import java.util.List;
import java.util.UUID;

@Getter
@Jacksonized @Builder
public class Cart {
    private final List<Item> items;
    private final UUID id;
    private Money totalPrice;

}

