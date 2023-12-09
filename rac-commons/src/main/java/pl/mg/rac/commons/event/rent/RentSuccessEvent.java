package pl.mg.rac.commons.event.rent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentSuccessPayload;

public class RentSuccessEvent extends RacEvent<RentSuccessPayload> {

    @JsonCreator
    public RentSuccessEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                            @JsonProperty("payload") RentSuccessPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_SUCCESS.getId();
    }

}
