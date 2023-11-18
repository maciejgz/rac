package pl.mg.rac.location.domain.model;

import lombok.Getter;
import pl.mg.rac.commons.value.Location;

import java.time.Instant;

@Getter
public class CarLocation {

    private final String vin;
    private final Location location;
    private final Instant timestamp;

    public CarLocation(String vin, Location location, Instant timestamp) {
        this.vin = vin;
        this.location = location;
        this.timestamp = timestamp;
    }
}
