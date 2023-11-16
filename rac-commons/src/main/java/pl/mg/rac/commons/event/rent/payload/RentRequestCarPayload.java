package pl.mg.rac.commons.event.rent.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record RentRequestCarPayload(String rentId, String username, String vin) implements RacEventPayload {
}
