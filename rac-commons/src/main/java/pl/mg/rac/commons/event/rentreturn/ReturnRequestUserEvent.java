package pl.mg.rac.commons.event.rentreturn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestUserPayload;

public class ReturnRequestUserEvent extends RacEvent<ReturnRequestUserPayload> {

    @JsonCreator
    public ReturnRequestUserEvent(@JsonProperty("aggregateId") String aggregateId,
                                  @JsonProperty("payload") ReturnRequestUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_USER.getId();
    }

}
