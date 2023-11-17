package pl.mg.rac.location.application.port.out;

import pl.mg.rac.location.domain.model.CarLocation;

public interface CarLocationDatabase {
    CarLocation saveCarLocation(String vin, CarLocation carLocation);

}
