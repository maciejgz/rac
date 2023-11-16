package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;

import java.time.Instant;

public record ReturnRequestLocationPayload(
        String rentId,
        String username,
        String vin,
        Instant rentStartDate
) implements RacEventPayload {
}
