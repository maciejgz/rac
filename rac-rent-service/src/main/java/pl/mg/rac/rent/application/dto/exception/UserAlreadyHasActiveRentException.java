package pl.mg.rac.rent.application.dto.exception;

public class UserAlreadyHasActiveRentException extends Exception {

    public UserAlreadyHasActiveRentException(String message) {
        super(message);
    }
}
