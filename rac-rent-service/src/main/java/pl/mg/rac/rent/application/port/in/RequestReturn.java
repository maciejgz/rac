package pl.mg.rac.rent.application.port.in;

import pl.mg.rac.rent.application.dto.command.RequestReturnCommand;
import pl.mg.rac.rent.application.dto.response.RequestReturnResponse;

public interface RequestReturn {

    RequestReturnResponse requestReturn(RequestReturnCommand command);

}
