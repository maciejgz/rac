package pl.mg.rac.user.domain.factory;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;

@Slf4j
public class UserFactory {

    public static User createUser(String name) {
        log.info("Creating user with name: {}", name);
        return new User(name, BigDecimal.ZERO);
    }
}
