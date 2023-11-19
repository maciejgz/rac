package pl.mg.rac.commons.event.rent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentRequestUserPayload;

public class RentRequestUserEvent extends RacEvent<RentRequestUserPayload> {
    @JsonCreator
    public RentRequestUserEvent(@JsonProperty("aggregateId") String aggregateId, @JsonProperty("payload") RentRequestUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_REQUEST_USER.getId();
    }

}
