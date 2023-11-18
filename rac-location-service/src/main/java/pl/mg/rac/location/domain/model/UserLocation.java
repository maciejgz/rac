package pl.mg.rac.location.domain.model;

import lombok.Getter;
import pl.mg.rac.commons.value.Location;

import java.time.Instant;
import java.util.UUID;

@Getter
public class UserLocation {

    private final UUID id;
    private final String username;
    private final Location location;
    private final Instant timestamp;

    public UserLocation(UUID id, String username, Location location, Instant timestamp) {
        this.id = id;
        this.username = username;
        this.location = location;
        this.timestamp = timestamp;
    }
}
