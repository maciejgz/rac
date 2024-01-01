package pl.mg.rac.user.application.port.in;

import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.query.GetUserQuery;
import pl.mg.rac.user.application.dto.response.UserResponse;

public interface GetUserPort {

    UserResponse getUser(GetUserQuery query) throws UserNotFoundException;
}
