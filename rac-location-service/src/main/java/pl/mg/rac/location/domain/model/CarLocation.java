package pl.mg.rac.location.domain.model;

import lombok.Getter;
import pl.mg.rac.commons.value.Location;

import java.time.Instant;
import java.util.UUID;

@Getter
public class CarLocation {

    private final UUID id;
    private final String vin;
    private final Location location;
    private final Instant timestamp;

    public CarLocation(UUID id, String vin, Location location, Instant timestamp) {
        this.id = id;
        this.vin = vin;
        this.location = location;
        this.timestamp = timestamp;
    }
}
