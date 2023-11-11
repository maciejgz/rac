package pl.mg.rac.commons.event.car.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record CarDeletedPayload(String vin) implements RacEventPayload {
}
