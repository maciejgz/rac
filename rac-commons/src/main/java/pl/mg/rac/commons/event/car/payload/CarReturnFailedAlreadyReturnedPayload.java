package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record CarReturnFailedAlreadyReturnedPayload(String vin, String rentalId, Location location,
                                                    double distanceTraveled) implements RacEventPayload {
}
