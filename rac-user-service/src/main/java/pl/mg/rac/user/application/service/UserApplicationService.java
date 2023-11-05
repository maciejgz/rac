package pl.mg.rac.user.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.user.application.dto.command.ChargeUserCommand;
import pl.mg.rac.user.application.dto.command.CreateUserCommand;
import pl.mg.rac.user.application.dto.command.DeleteUserCommand;
import pl.mg.rac.user.application.dto.exception.UserChargeException;
import pl.mg.rac.user.application.dto.exception.UserDeletionException;
import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.exception.UserRegistrationException;
import pl.mg.rac.user.application.dto.query.GetUserQuery;
import pl.mg.rac.user.application.dto.response.ChargeUserResponse;
import pl.mg.rac.user.application.dto.response.CreateUserResponse;
import pl.mg.rac.user.application.dto.response.UserResponse;
import pl.mg.rac.user.application.port.in.ChargeUserPort;
import pl.mg.rac.user.application.port.in.CreateUserPort;
import pl.mg.rac.user.application.port.in.DeleteUserPort;
import pl.mg.rac.user.application.port.in.GetUserPort;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.domain.exception.UserAlreadyRegisteredException;
import pl.mg.rac.user.domain.exception.UserNotExistException;
import pl.mg.rac.user.domain.model.User;
import pl.mg.rac.user.domain.service.UserDomainService;

import java.util.List;

/**
 * Responsible for transaction handling and domain services coordination - it is a facade for domain services.
 */
@Slf4j
public class UserApplicationService implements CreateUserPort, DeleteUserPort, ChargeUserPort, GetUserPort {

    private final UserDomainService userDomainService;
    private final UserEventPublisher userEventPublisher;

    public UserApplicationService(UserDomainService userDomainService, UserEventPublisher userEventPublisher) {
        this.userDomainService = userDomainService;
        this.userEventPublisher = userEventPublisher;
    }

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserCommand command) throws UserRegistrationException {
        log.debug("createUser() called with: command = [" + command + "]");
        try {
            User user = userDomainService.createUser(command.name());
            user.getEvents().forEach(userEventPublisher::publishUserEvent);
            return new CreateUserResponse(user.getName(), user.getBalance());
        } catch (UserAlreadyRegisteredException e) {
            throw new UserRegistrationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ChargeUserResponse chargeUser(ChargeUserCommand command) throws UserChargeException {
        log.debug("chargeUser() called with: command = [" + command + "]");
        try {
            User user = userDomainService.chargeUser(command.name(), command.amount());
            user.getEvents().forEach(userEventPublisher::publishUserEvent);
            return new ChargeUserResponse(user.getName(), user.getBalance());
        } catch (UserNotExistException e) {
            throw new UserChargeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteUser(DeleteUserCommand command) throws UserDeletionException {
        log.debug("deleteUser() called with: command = [" + command + "]");
        try {
            List<RacEvent<?>> domainEvents = userDomainService.deleteUser(command.name());
            domainEvents.forEach(userEventPublisher::publishUserEvent);
        } catch (UserNotExistException e) {
            throw new UserDeletionException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(GetUserQuery query) throws UserNotFoundException {
        log.debug("getUser() called with: name = [" + query + "]");
        try {
            User user = userDomainService.getUser(query.name());
            return new UserResponse(user.getName(), user.getBalance());
        } catch (UserNotExistException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }
}
