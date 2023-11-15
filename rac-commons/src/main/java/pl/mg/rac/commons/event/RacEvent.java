package pl.mg.rac.commons.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class RacEvent<T extends RacEventPayload> {

    private final UUID eventId;
    private final Instant timestamp;
    private final String aggregateId;
    private final T payload;

    @JsonCreator
    public RacEvent(@JsonProperty("aggregateId") String aggregateId, @JsonProperty("payload") T payload) {
        this.eventId = UUID.randomUUID();
        this.timestamp = Instant.now();
        this.aggregateId = aggregateId;
        this.payload = payload;
    }

    public abstract String getEventType();

}

