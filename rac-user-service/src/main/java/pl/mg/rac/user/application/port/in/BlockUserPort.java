package pl.mg.rac.user.application.port.in;

import pl.mg.rac.user.application.dto.command.BlockUserCommand;
import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.response.BlockUserResponse;

public interface BlockUserPort {

    BlockUserResponse block(BlockUserCommand command) throws UserNotFoundException;
}
