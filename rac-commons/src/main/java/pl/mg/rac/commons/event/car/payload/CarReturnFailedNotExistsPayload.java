package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record CarReturnFailedNotExistsPayload(String vin, String rentalId, Location location,
                                              BigDecimal distanceTraveled) implements RacEventPayload {
}
