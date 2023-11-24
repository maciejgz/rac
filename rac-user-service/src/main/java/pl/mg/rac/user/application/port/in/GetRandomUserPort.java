package pl.mg.rac.user.application.port.in;

import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.exception.UserSearchException;
import pl.mg.rac.user.application.dto.response.UserResponse;

public interface GetRandomUserPort {

    UserResponse getRandomUser() throws UserSearchException, UserNotFoundException;
}
