package pl.mg.rac.user.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.UserCreatedEvent;
import pl.mg.rac.commons.event.user.UserDeletedEvent;
import pl.mg.rac.commons.event.user.payload.UserCreatedPayload;
import pl.mg.rac.commons.event.user.payload.UserDeletedPayload;
import pl.mg.rac.user.application.dto.command.*;
import pl.mg.rac.user.application.dto.exception.UserChargeException;
import pl.mg.rac.user.application.dto.exception.UserDeletionException;
import pl.mg.rac.user.application.dto.exception.UserNotFoundException;
import pl.mg.rac.user.application.dto.exception.UserRegistrationException;
import pl.mg.rac.user.application.dto.query.GetUserQuery;
import pl.mg.rac.user.application.dto.response.*;
import pl.mg.rac.user.application.port.in.*;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.domain.exception.UserNotExistException;
import pl.mg.rac.user.domain.factory.UserFactory;
import pl.mg.rac.user.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Responsible for transaction handling and domain services coordination - it is a facade for domain services.
 */
@Slf4j
public class UserApplicationService implements CreateUserPort, DeleteUserPort, ChargeUserPort, GetUserPort, GetRandomUserPort, BlockUserPort, UnblockUserPort {

    private final UserEventPublisher userEventPublisher;
    private final UserDatabase userDatabase;

    public UserApplicationService(UserEventPublisher userEventPublisher, UserDatabase userDatabase) {
        this.userEventPublisher = userEventPublisher;
        this.userDatabase = userDatabase;
    }

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserCommand command) throws UserRegistrationException {
        log.debug("createUser() called with: command = [" + command + "]");
        if (userDatabase.exists(command.name())) {
            throw new UserRegistrationException("User with name " + command.name() + " already exists");
        }
        User user = UserFactory.createNewUser(command.name(), command.location());
        user.addEvent(new UserCreatedEvent(user.getName(), new UserCreatedPayload(user.getName(), user.getLocation())));
        User userSaved = userDatabase.save(user);
        user.getEvents().forEach(userEventPublisher::publishUserEvent);
        return new CreateUserResponse(userSaved.getName(), userSaved.getBalance());
    }

    @Override
    @Transactional
    public ChargeUserResponse chargeUser(ChargeUserCommand command) throws UserChargeException {
        log.debug("chargeUser() called with: command = [" + command + "]");
        try {
            var user = userDatabase.findByName(command.name()).orElseThrow(
                    () -> new UserNotExistException("User with name " + command.name() + " not exists"));
            user.charge(command.amount());
            user = userDatabase.save(user);
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
            Optional<User> userOpt = userDatabase.findByName(command.name());
            if (userOpt.isPresent() && userOpt.get().getCurrentRentId() != null) {
                throw new UserDeletionException("User with name " + command.name() + " has active rent");
            }
            userDatabase.delete(userDatabase.findByName(command.name()).orElseThrow(
                    () -> new UserNotExistException("User with name " + command.name() + " not exists")));
            List<RacEvent<?>> domainEvents = List.of(new UserDeletedEvent(command.name(), new UserDeletedPayload(command.name())));
            domainEvents.forEach(userEventPublisher::publishUserEvent);
        } catch (UserNotExistException e) {
            throw new UserDeletionException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(GetUserQuery query) throws UserNotFoundException {
        log.debug("getUser() called with: name = [" + query + "]");
        User user = userDatabase.findByName(query.name()).orElseThrow(
                () -> new UserNotFoundException("User with name " + query.name() + " not exists"));
        return new UserResponse(user.getName(), user.getBalance(), user.getLocation());
    }

    @Override
    public UserResponse getRandomUser() throws UserNotFoundException {
        log.debug("getRandomUser() called");
        Optional<User> user = userDatabase.getRandomUser();
        if (user.isEmpty()) {
            throw new UserNotFoundException("No users in database");
        } else {
            return new UserResponse(user.get().getName(), user.get().getBalance(), user.get().getLocation());
        }
    }

    @Override
    public BlockUserResponse block(BlockUserCommand command) throws UserNotFoundException {
        log.debug("block() called with: command = [" + command + "]");
        Optional<User> user = userDatabase.findByName(command.username());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with name " + command.username() + " not exists");
        } else {
            user.get().block();
            userDatabase.save(user.get());
            return new BlockUserResponse(user.get().getName(), user.get().isBlocked());
        }
    }

    @Override
    public UnblockUserResponse unblock(UnblockUserCommand command) throws UserNotFoundException {
        log.debug("unblock() called with: command = [" + command + "]");
        Optional<User> user = userDatabase.findByName(command.username());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with name " + command.username() + " not exists");
        } else {
            user.get().unblock();
            userDatabase.save(user.get());
            return new UnblockUserResponse(user.get().getName(), user.get().isBlocked());
        }
    }
}
