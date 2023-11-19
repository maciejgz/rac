package pl.mg.rac.commons.event.rent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentRequestCarPayload;

public class RentRequestCarEvent extends RacEvent<RentRequestCarPayload> {
    @JsonCreator
    public RentRequestCarEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                               @JsonProperty("payload") RentRequestCarPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_REQUEST_CAR.getId();
    }

}
