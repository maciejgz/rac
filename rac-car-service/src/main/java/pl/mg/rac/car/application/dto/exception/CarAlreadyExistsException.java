package pl.mg.rac.car.application.dto.exception;

public class CarAlreadyExistsException extends Exception {

    public CarAlreadyExistsException(String message) {
        super(message);
    }
}
