package pl.mg.rac.commons.event.rent.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record RentSuccessPayload(String rentId, String username, String vin) implements RacEventPayload {
}
