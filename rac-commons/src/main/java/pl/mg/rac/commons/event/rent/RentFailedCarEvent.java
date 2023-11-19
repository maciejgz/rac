package pl.mg.rac.commons.event.rent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentFailedCarPayload;

public class RentFailedCarEvent extends RacEvent<RentFailedCarPayload> {

    @JsonCreator
    public RentFailedCarEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                              @JsonProperty("payload") RentFailedCarPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_CAR.getId();
    }

}
