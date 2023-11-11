package pl.mg.rac.car.domain.exception;

public class CarAlreadyReturnedException extends Exception {

    public CarAlreadyReturnedException(String message) {
        super(message);
    }
}
