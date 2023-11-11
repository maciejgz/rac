package pl.mg.rac.car.domain.exception;

public class CarAlreadyRegisteredException extends Exception {

    public CarAlreadyRegisteredException(String message) {
        super(message);
    }
}
