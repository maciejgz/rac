package pl.mg.rac.commons.event.rentreturn.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record ReturnFailedLocationPayload(String rentId, String username, String vin,
                                          String errorCode, String errorMessage) implements RacEventPayload {
}
