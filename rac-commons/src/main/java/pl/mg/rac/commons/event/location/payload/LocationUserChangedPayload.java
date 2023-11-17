package pl.mg.rac.commons.event.location.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record LocationUserChangedPayload(String name, Location location) implements RacEventPayload {
}
