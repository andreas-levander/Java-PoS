package app.errors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
