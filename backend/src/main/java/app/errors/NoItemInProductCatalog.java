package app.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoItemInProductCatalog extends RuntimeException {
    private String message;

    public NoItemInProductCatalog(String message) {
        super(message);
        this.message = message;
    }
}
