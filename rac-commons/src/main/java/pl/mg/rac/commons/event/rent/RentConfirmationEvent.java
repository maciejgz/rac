package pl.mg.rac.commons.event.rent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentConfirmationPayload;

public class RentConfirmationEvent extends RacEvent<RentConfirmationPayload> {

    @JsonCreator
    public RentConfirmationEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                                 @JsonProperty("payload") RentConfirmationPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_CONFIRMATION.getId();
    }

}
