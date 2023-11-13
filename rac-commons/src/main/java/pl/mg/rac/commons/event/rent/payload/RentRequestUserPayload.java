package pl.mg.rac.commons.event.rent.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record RentRequestUserPayload(String rentId, String username, String vin) implements RacEventPayload {
}
