package pl.mg.rac.car.domain.exception;

public class CarNotRentedException extends Exception {

    public CarNotRentedException(String message) {
        super(message);
    }
}
