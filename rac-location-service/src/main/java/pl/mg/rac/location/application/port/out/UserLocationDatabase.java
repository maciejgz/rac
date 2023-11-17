package pl.mg.rac.location.application.port.out;

import pl.mg.rac.location.domain.model.CarLocation;

public interface UserLocationDatabase {
    CarLocation saveUserLocation(String username, CarLocation carLocation);

}
