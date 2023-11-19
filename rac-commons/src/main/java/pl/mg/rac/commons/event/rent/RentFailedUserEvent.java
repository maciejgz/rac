package pl.mg.rac.commons.event.rent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentFailedUserPayload;

public class RentFailedUserEvent extends RacEvent<RentFailedUserPayload> {

    @JsonCreator
    public RentFailedUserEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                               @JsonProperty(value = "payload") RentFailedUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_USER.getId();
    }

}
