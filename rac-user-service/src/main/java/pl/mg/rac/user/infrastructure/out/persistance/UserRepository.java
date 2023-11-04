package pl.mg.rac.user.infrastructure.out.persistance;

import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

public class UserRepository implements UserDatabase {
    @Override
    public boolean exists(String name) {
        //TODO można tu tłumaczyć obiekt User na obiekt UserEntity - dzięki temu nie ma @Entity w agregacie
        return false;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }
}
