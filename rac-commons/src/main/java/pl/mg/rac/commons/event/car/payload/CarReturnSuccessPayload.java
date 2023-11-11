package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record CarReturnSuccessPayload(String vin, String rentalId, Location location, double distanceTraveled,
                                      double mileage) implements RacEventPayload {
}
