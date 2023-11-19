package pl.mg.rac.commons.event.rentreturn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnConfirmationPayload;

public class ReturnConfirmationEvent extends RacEvent<ReturnConfirmationPayload> {

    @JsonCreator
    public ReturnConfirmationEvent(@JsonProperty("aggregateId") String aggregateId,@JsonProperty("payload")  ReturnConfirmationPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_CONFIRMATION.getId();
    }

}
