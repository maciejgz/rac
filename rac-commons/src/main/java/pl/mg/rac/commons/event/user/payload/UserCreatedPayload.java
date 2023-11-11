package pl.mg.rac.commons.event.user.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record UserCreatedPayload(String username) implements RacEventPayload {
}
