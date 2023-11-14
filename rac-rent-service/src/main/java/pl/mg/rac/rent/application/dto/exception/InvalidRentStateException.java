package pl.mg.rac.rent.application.dto.exception;

public class InvalidRentStateException extends Exception {

    public InvalidRentStateException(String message) {
        super(message);
    }

    public InvalidRentStateException(String message, Exception e) {
        super(message, e);
    }
}
