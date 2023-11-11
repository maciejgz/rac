package pl.mg.rac.car.domain.exception;

public class CarAlreadyDeletedException extends Exception {

    public CarAlreadyDeletedException(String message) {
        super(message);
    }
}
