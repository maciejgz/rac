package pl.mg.rac.user.domain.service;


import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.UserCreatedEvent;
import pl.mg.rac.commons.event.user.UserDeletedEvent;
import pl.mg.rac.commons.event.user.payload.UserCreatedPayload;
import pl.mg.rac.commons.event.user.payload.UserDeletedPayload;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.exception.UserAlreadyRegisteredException;
import pl.mg.rac.user.domain.exception.UserNotExistException;
import pl.mg.rac.user.domain.factory.UserFactory;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class UserDomainService {

    //TODO move to application service
    private final UserDatabase userDatabase;

    public UserDomainService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public User createUser(String name) throws UserAlreadyRegisteredException {
        log.debug("createUser() called with: name = [" + name + "]");
        //TODO database object should be used by an application service, here should be just a domain logic
        if (userDatabase.exists(name)) {
            throw new UserAlreadyRegisteredException("User with name " + name + " already exists");
        }
        User user = UserFactory.createUser(name);
        user.addEvent(new UserCreatedEvent(user.getName(), new UserCreatedPayload(user.getName())));
        return userDatabase.save(user);
    }

    public List<RacEvent<?>> deleteUser(String name) throws UserNotExistException {
        //TODO move to application service, domain event always should be generated in domain object or in the incoming port adapter (application service)
        log.debug("deleteUser() called with: name = [" + name + "]");
        userDatabase.delete(userDatabase.findByName(name).orElseThrow(() -> new UserNotExistException("User with name " + name + " not exists")));
        return List.of(new UserDeletedEvent(name, new UserDeletedPayload(name)));
    }

    public User chargeUser(String name, BigDecimal amount) throws UserNotExistException {
        //TODO move to application service
        log.debug("chargeUser() called with: name = [" + name + "], amount = [" + amount + "]");
        var user = userDatabase.findByName(name).orElseThrow(() -> new UserNotExistException("User with name " + name + " not exists"));
        user.charge(amount);
        userDatabase.save(user);
        return user;
    }

    public User getUser(String name) throws UserNotExistException {
        //TODO move to application service
        log.debug("getUser() called with: name = [" + name + "]");
        return userDatabase.findByName(name).orElseThrow(() -> new UserNotExistException("User with name " + name + " not exists"));
    }
}
