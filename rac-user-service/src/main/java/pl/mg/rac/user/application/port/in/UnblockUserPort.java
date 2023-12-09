package pl.mg.rac.user.application.port.in;

import pl.mg.rac.user.application.dto.command.UnblockUserCommand;
import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.response.UnblockUserResponse;

public interface UnblockUserPort {

    UnblockUserResponse unblock(UnblockUserCommand command) throws UserNotFoundException;
}
