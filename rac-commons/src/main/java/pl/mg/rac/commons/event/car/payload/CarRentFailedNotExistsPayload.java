package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record CarRentFailedNotExistsPayload(String vin, String rentalId) implements RacEventPayload {
}
