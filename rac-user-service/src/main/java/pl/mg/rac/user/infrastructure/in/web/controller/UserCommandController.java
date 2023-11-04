package pl.mg.rac.user.infrastructure.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.user.application.dto.command.ChargeUserCommand;
import pl.mg.rac.user.application.dto.command.CreateUserCommand;
import pl.mg.rac.user.application.dto.command.DeleteUserCommand;
import pl.mg.rac.user.application.dto.exception.UserChargeException;
import pl.mg.rac.user.application.dto.exception.UserDeletionException;
import pl.mg.rac.user.application.dto.exception.UserRegistrationException;
import pl.mg.rac.user.application.dto.response.ChargeUserResponse;
import pl.mg.rac.user.application.dto.response.CreateUserResponse;
import pl.mg.rac.user.application.facade.UserFacade;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserCommandController {

    private final UserFacade userFacade;

    public UserCommandController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping(value = "")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserCommand command) throws UserRegistrationException, URISyntaxException {
        log.debug("Blacklist user {}", command);
        CreateUserResponse user = userFacade.createUser(command);
        return ResponseEntity.created(new URI("/user/" + user.name())).body(user);
    }

    @PostMapping(value = "/{name}")
    public ResponseEntity<Void> deleteUser(@PathVariable String name) throws UserDeletionException {
        userFacade.deleteUser(new DeleteUserCommand(name));
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{name}/charge")
    public ResponseEntity<ChargeUserResponse> deleteUser(@RequestBody ChargeUserCommand command) throws UserChargeException {
        ChargeUserResponse chargeUserResponse = userFacade.chargeUser(command);
        return ResponseEntity.ok(chargeUserResponse);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRegistrationException(UserRegistrationException e) {
        //TODO return JSON object instead of plain text
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleDeletionException(UserDeletionException e) {
        //TODO return JSON object instead of plain text
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleChargeException(UserChargeException e) {
        //TODO return JSON object instead of plain text
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
