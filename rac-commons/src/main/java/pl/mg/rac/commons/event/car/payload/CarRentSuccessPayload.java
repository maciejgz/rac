package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record CarRentSuccessPayload(String vin, String rentalId) implements RacEventPayload {
}
