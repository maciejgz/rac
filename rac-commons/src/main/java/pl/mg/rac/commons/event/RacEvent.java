package pl.mg.rac.commons.event;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class RacEvent<T extends RacEventPayload> {

    private final UUID eventId;
    private final Instant timestamp;
    private final String aggregateId;
    private final T payload;

    public RacEvent(String aggregateId, T payload) {
        this.eventId = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.aggregateId = aggregateId;
        this.payload = payload;
    }

    public abstract String getEventType();

}

