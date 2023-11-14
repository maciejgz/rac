package pl.mg.rac.rent.application.port.in;

import pl.mg.rac.rent.application.dto.command.RequestReturnCommand;
import pl.mg.rac.rent.application.dto.exception.InvalidRentStateException;
import pl.mg.rac.rent.application.dto.exception.RentNotFoundException;
import pl.mg.rac.rent.application.dto.response.RequestReturnResponse;

public interface RequestReturn {

    RequestReturnResponse requestReturn(RequestReturnCommand command) throws RentNotFoundException, InvalidRentStateException;

}
