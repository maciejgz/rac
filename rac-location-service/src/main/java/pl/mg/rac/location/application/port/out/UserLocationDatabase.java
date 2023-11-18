package pl.mg.rac.location.application.port.out;

import pl.mg.rac.location.domain.model.UserLocation;

public interface UserLocationDatabase {
    UserLocation saveUserLocation(UserLocation carLocation);

    UserLocation findLatestLocation(String username);

}
