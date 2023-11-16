package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

public record ReturnConfirmationPayload(String rentId, String username, String vin,
                                        Location endLocation, double distanceTraveled) implements RacEventPayload {
}
