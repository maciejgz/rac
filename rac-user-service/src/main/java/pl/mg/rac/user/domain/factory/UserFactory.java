package pl.mg.rac.user.domain.factory;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
public class UserFactory {

    public static User createNewUser(String name, Location location) {
        log.info("Creating user with name: {}", name);
        return new User(name, BigDecimal.ZERO, location, LocalDate.now());
    }
}
