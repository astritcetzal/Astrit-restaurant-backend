package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.exception;

public class OrderValidationException extends RuntimeException {

    public OrderValidationException(String message) {
        super(message);
    }
}
