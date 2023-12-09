package pl.mg.rac.rent.application.dto.exception;

public class CarAlreadyHasActiveRentException extends Exception {

    public CarAlreadyHasActiveRentException(String message) {
        super(message);
    }
}
