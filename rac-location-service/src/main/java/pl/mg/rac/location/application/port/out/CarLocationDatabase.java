package pl.mg.rac.location.application.port.out;

import pl.mg.rac.location.application.dto.exception.LocationNotFoundException;
import pl.mg.rac.location.domain.model.CarLocation;

import java.time.Instant;
import java.util.List;

public interface CarLocationDatabase {
    void saveCarLocation(CarLocation carLocation);

    List<CarLocation> findLocationBetween(String vin, Instant from, Instant to);

    CarLocation findLatestLocation(String vin) throws LocationNotFoundException;
}
