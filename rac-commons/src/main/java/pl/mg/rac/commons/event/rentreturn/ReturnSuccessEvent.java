package pl.mg.rac.commons.event.rentreturn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnSuccessPayload;

public class ReturnSuccessEvent extends RacEvent<ReturnSuccessPayload> {

    @JsonCreator
    public ReturnSuccessEvent(@JsonProperty("aggregateId") String aggregateId, @JsonProperty("payload") ReturnSuccessPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_SUCCESS.getId();
    }

}
