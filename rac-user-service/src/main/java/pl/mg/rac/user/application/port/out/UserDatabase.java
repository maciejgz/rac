package pl.mg.rac.user.application.port.out;

import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

public interface UserDatabase {

    public boolean exists(String name);

    public User save(User user);

    public void delete(User user);

    public Optional<User> findByName(String name);

}
