package app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.javamoney.moneta.Money;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Jacksonized @Builder
public class Cart {
    private final List<Item> items;
    private final UUID id;
    private Money totalPrice;

}

