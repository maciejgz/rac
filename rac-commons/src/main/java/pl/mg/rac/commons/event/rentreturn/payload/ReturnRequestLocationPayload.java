package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record ReturnRequestLocationPayload(
        String rentId,
        String username,
        String vin
) implements RacEventPayload {
}
