package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;
import java.time.Instant;

public record ReturnRequestCarPayload(String rentId, String username, String vin,
                                      BigDecimal distanceTraveled,
                                      Instant rentStartDate,
                                      BigDecimal chargedAmount,
                                      Location endLocation
                                      ) implements RacEventPayload {
}
