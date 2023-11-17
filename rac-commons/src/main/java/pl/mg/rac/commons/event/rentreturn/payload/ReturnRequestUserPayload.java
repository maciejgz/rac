package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;

import java.time.Instant;

public record ReturnRequestUserPayload(String rentId,
                                       String username,
                                       String vin,
                                       double distanceTraveled,
                                       Instant rentStartDate
) implements RacEventPayload {
}
