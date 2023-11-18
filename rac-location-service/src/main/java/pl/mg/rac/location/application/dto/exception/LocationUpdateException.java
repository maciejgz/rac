package pl.mg.rac.location.application.dto.exception;

public class LocationUpdateException extends Exception {

    public LocationUpdateException(String message) {
        super(message);
    }

    public LocationUpdateException(String message, Exception e) {
        super(message, e);
    }
}
