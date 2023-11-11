package pl.mg.rac.commons.event.user.payload;

import pl.mg.rac.commons.event.RacEventPayload;

public record UserDeletedPayload(String username) implements RacEventPayload {
}
