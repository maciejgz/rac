package pl.mg.rac.rent.application.port.in;

import pl.mg.rac.rent.application.dto.command.RequestRentCommand;
import pl.mg.rac.rent.application.dto.exception.CarAlreadyHasActiveRentException;
import pl.mg.rac.rent.application.dto.exception.UserAlreadyHasActiveRentException;
import pl.mg.rac.rent.application.dto.response.RequestRentResponse;

public interface RequestRent {

    public RequestRentResponse requestRent(RequestRentCommand command) throws UserAlreadyHasActiveRentException, CarAlreadyHasActiveRentException;

}
