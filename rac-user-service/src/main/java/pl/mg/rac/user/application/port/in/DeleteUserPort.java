package pl.mg.rac.user.application.port.in;

import pl.mg.rac.user.application.dto.command.DeleteUserCommand;
import pl.mg.rac.user.application.dto.exception.UserDeletionException;

public interface DeleteUserPort {

    void deleteUser(DeleteUserCommand command) throws UserDeletionException;
}
