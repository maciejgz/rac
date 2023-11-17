package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record ReturnFailedCarPayload(String rentId, String username, String vin,
                                     Location endLocation, double distanceTraveled, BigDecimal chargedAmount,
                                     String errorCode, String errorMessage) implements RacEventPayload {
}
