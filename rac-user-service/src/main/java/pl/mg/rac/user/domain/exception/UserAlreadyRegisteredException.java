package pl.mg.rac.user.domain.exception;

public class UserAlreadyRegisteredException extends Exception {

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
