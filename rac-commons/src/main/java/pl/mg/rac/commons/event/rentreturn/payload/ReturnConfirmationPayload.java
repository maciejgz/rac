package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record ReturnConfirmationPayload(String rentId, String username, String vin,
                                        Location endLocation, double distanceTraveled,
                                        BigDecimal chargedAmount) implements RacEventPayload {
}
