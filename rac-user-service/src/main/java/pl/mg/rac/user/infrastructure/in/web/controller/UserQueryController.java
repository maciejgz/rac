package pl.mg.rac.user.infrastructure.in.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.commons.api.dto.ApiError;
import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.exception.UserSearchException;
import pl.mg.rac.user.application.dto.query.GetUserQuery;
import pl.mg.rac.user.application.dto.response.UserResponse;
import pl.mg.rac.user.application.facade.UserFacade;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserQueryController {

    private final UserFacade userFacade;

    public UserQueryController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping(value = "/random")
    public ResponseEntity<UserResponse> getRandomUser() throws UserSearchException, UserNotFoundException {
        log.info("getRandomUser() called");
        UserResponse user = userFacade.getRandomUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String name) throws UserSearchException, UserNotFoundException {
        UserResponse user = userFacade.getUser(new GetUserQuery(name));
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleException(UserNotFoundException ignoredE) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleException(UserSearchException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(), e.getStackTrace()));
    }

}
