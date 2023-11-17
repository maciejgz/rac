package pl.mg.rac.location.infrastructure.out.persistence;

import pl.mg.rac.location.application.port.out.UserLocationDatabase;
import pl.mg.rac.location.domain.model.CarLocation;

public class UserLocationRepository implements UserLocationDatabase {

    private final UserLocationJpaRepository userRepository;

    public UserLocationRepository(UserLocationJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CarLocation saveUserLocation(String username, CarLocation carLocation) {
        //TODO implement
        return null;
    }
}
