package pl.mg.rac.commons.event.user.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record UserCreatedPayload(String username, Location location) implements RacEventPayload {
}
