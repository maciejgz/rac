package pl.mg.rac.location.infrastructure.out.persistence;

import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.port.out.UserLocationDatabase;
import pl.mg.rac.location.domain.model.UserLocation;
import pl.mg.rac.location.infrastructure.out.persistence.entity.UserLocationEntity;

import java.math.BigDecimal;

public class UserLocationRepository implements UserLocationDatabase {

    private final UserLocationJpaRepository userRepository;

    public UserLocationRepository(UserLocationJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLocation saveUserLocation(UserLocation userLocation) {
        UserLocationEntity entity = new UserLocationEntity(
                userLocation.getId(),
                userLocation.getUsername(),
                BigDecimal.valueOf(userLocation.getLocation().latitude()),
                BigDecimal.valueOf(userLocation.getLocation().longitude()),
                userLocation.getTimestamp()
        );
        return ofEntity(userRepository.save(entity));
    }

    @Override
    public UserLocation findLatestLocation(String username) {
        return ofEntity(userRepository.findFirstByUsernameOrderByTimestampDesc(username));
    }

    private static UserLocation ofEntity(UserLocationEntity entity) {
        return new UserLocation(
                entity.getId(),
                entity.getUsername(),
                new Location(entity.getLatitude().doubleValue(), entity.getLongitude().doubleValue()),
                entity.getTimestamp()
        );
    }
}
