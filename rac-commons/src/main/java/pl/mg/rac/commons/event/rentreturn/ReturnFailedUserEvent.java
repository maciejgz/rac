package pl.mg.rac.commons.event.rentreturn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnFailedUserPayload;

public class ReturnFailedUserEvent extends RacEvent<ReturnFailedUserPayload> {

    @JsonCreator
    public ReturnFailedUserEvent(@JsonProperty("aggregateId") String aggregateId, @JsonProperty("payload")  ReturnFailedUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_USER.getId();
    }

}
