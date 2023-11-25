package pl.mg.rac.user.infrastructure.out.persistence;

import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.model.User;
import pl.mg.rac.user.infrastructure.out.persistence.entity.UserEntity;

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
        return this.mapToDomainUser(userJpaRepository.save(UserEntity.ofUser(user)));
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(UserEntity.ofUser(user));
    }

    @Override
    public Optional<User> findByName(String name) {
        return userJpaRepository.findByName(name).map(this::mapToDomainUser);
    }

    @Override
    public Optional<User> getRandomUser() {
        return userJpaRepository.findRandomUser().map(this::mapToDomainUser);
    }

    private User mapToDomainUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(),
                userEntity.getBalance(), userEntity.getLocation(),
                userEntity.getCurrentRentId());
    }
}
