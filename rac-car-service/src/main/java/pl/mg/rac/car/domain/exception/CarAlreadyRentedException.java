package pl.mg.rac.car.domain.exception;

public class CarAlreadyRentedException extends Exception {

    public CarAlreadyRentedException(String message) {
        super(message);
    }
}
