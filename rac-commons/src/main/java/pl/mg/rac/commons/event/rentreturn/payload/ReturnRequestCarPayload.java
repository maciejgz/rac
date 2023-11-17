package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;

import java.math.BigDecimal;
import java.time.Instant;

public record ReturnRequestCarPayload(String rentId, String username, String vin,
                                      double distanceTraveled,
                                      Instant rentStartDate,
                                      BigDecimal chargedAmount) implements RacEventPayload {
}
