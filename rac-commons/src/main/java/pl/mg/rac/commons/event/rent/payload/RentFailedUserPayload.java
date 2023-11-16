package pl.mg.rac.commons.event.rent.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record RentFailedUserPayload(String rentId, String username, String vin,
                                    String errorCode, String errorMessage) implements RacEventPayload {
}
