package pl.mg.rac.location.infrastructure.out.persistence;

import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.dto.exception.LocationNotFoundException;
import pl.mg.rac.location.application.port.out.UserLocationDatabase;
import pl.mg.rac.location.domain.model.UserLocation;
import pl.mg.rac.location.infrastructure.out.persistence.entity.UserLocationEntity;

import java.math.BigDecimal;
import java.util.Optional;

public class UserLocationRepository implements UserLocationDatabase {

    private final UserLocationJpaRepository userRepository;

    public UserLocationRepository(UserLocationJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLocation saveUserLocation(UserLocation userLocation) {
        UserLocationEntity entity = new UserLocationEntity(
                new UserLocationEntity.UserKey(userLocation.getUsername(), userLocation.getTimestamp()),
                userLocation.getLocation().latitude(),
                userLocation.getLocation().longitude()
        );
        return ofEntity(userRepository.save(entity));
    }

    @Override
    public UserLocation findLatestLocation(String username) throws LocationNotFoundException {
        Optional<UserLocationEntity> user = userRepository.findFirstByKeyUsernameOrderByKeyTimestampDesc(username);
        if (user.isPresent()) {
            return ofEntity(user.get());
        } else {
            throw new LocationNotFoundException("Location for username " + username + " not found");
        }
    }

    private static UserLocation ofEntity(UserLocationEntity entity) {
        return new UserLocation(
                entity.getKey().getUsername(),
                new Location(entity.getLatitude(), entity.getLongitude()),
                entity.getKey().getTimestamp()
        );
    }
}
