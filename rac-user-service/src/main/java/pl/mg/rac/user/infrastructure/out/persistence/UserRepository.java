package pl.mg.rac.user.infrastructure.out.persistence;

import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

public class UserRepository implements UserDatabase {

    private final UserJpaRepository userJpaRepository;

    public UserRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public boolean exists(String name) {
        return userJpaRepository.existsByName(name);
    }

    @Override
    public User save(User user) {
        //TODO można tu tłumaczyć obiekt User na obiekt UserEntity - dzięki temu nie ma @Document w agregacie, ale wymaga dodatkowego mappera
        return userJpaRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userJpaRepository.findByName(name);
    }
}
