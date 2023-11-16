package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record ReturnRequestCarPayload(String rentId, String username, String vin) implements RacEventPayload {
}
