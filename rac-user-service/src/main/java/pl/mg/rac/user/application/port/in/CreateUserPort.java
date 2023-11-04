package pl.mg.rac.user.application.port.in;

import pl.mg.rac.user.application.dto.command.CreateUserCommand;
import pl.mg.rac.user.application.dto.exception.UserRegistrationException;
import pl.mg.rac.user.application.dto.response.CreateUserResponse;

public interface CreateUserPort {

    CreateUserResponse createUser(CreateUserCommand createUserCommand) throws UserRegistrationException;
}
