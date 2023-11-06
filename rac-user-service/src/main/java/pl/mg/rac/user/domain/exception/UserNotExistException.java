package pl.mg.rac.user.domain.exception;

public class UserNotExistException extends Exception {

    public UserNotExistException(String message) {
        super(message);
    }
}
