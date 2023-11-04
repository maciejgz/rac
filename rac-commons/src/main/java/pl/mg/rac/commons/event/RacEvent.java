package pl.mg.rac.commons.event;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class RacEvent {

    private final UUID eventId;
    private final Instant timestamp;
    private final String aggregateId;

    public RacEvent(String aggregateId) {
        this.eventId = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.aggregateId = aggregateId;
    }

    public abstract String getEventType();

    public abstract Object getPayload();
}

