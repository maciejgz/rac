package pl.mg.rac.commons.event.location.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record LocationCarChangedPayload(String vin, Location location) implements RacEventPayload {
}
