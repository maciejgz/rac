package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record CarCreatedPayload(String vin, Location location, double mileage) implements RacEventPayload {
}
