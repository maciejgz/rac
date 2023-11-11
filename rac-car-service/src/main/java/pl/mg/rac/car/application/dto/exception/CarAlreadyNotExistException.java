package pl.mg.rac.car.application.dto.exception;

public class CarAlreadyNotExistException extends Exception {

    public CarAlreadyNotExistException(String message) {
        super(message);
    }
}
