package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record CarReturnSuccessPayload(String vin, String rentalId, Location finalLocation, BigDecimal distanceTraveled,
                                      BigDecimal mileage) implements RacEventPayload {
}
