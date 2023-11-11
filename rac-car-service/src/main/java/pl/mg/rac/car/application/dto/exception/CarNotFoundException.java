package pl.mg.rac.car.application.dto.exception;

public class CarNotFoundException extends Exception {

    public CarNotFoundException(String message) {
        super(message);
    }
}
