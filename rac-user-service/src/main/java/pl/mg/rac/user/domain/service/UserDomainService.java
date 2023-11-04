package pl.mg.rac.user.domain.service;


import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.exception.UserAlreadyRegisteredException;
import pl.mg.rac.user.domain.exception.UserNotExistException;
import pl.mg.rac.user.domain.factory.UserFactory;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;

@Slf4j
public class UserDomainService {

    private final UserDatabase userDatabase;

    public UserDomainService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public User createUser(String name) throws UserAlreadyRegisteredException {
        log.debug("createUser() called with: name = [" + name + "]");
        if (userDatabase.exists(name)) {
            throw new UserAlreadyRegisteredException("User with name " + name + " already exists");
        }
        return userDatabase.save(UserFactory.createUser(name));
    }

    public void deleteUser(String name) throws UserNotExistException {
        log.debug("deleteUser() called with: name = [" + name + "]");
        userDatabase.delete(userDatabase.findByName(name).orElseThrow(() -> new UserNotExistException("User with name " + name + " not exists")));
    }

    public User chargeUser(String name, BigDecimal amount) throws UserNotExistException {
        log.debug("chargeUser() called with: name = [" + name + "], amount = [" + amount + "]");
        var user = userDatabase.findByName(name).orElseThrow(() -> new UserNotExistException("User with name " + name + " not exists"));
        user.charge(amount);
        userDatabase.save(user);
        return user;
    }

    public User getUser(String name) throws UserNotExistException {
        log.debug("getUser() called with: name = [" + name + "]");
        return userDatabase.findByName(name).orElseThrow(() -> new UserNotExistException("User with name " + name + " not exists"));
    }
}
