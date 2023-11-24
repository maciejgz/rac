package pl.mg.rac.user.application.port.out;

import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

public interface UserDatabase {

    boolean exists(String name);

    User save(User user);

    void delete(User user);

    Optional<User> findByName(String name);

    Optional<User> getRandomUser();
}
