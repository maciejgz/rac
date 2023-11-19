package pl.mg.rac.commons.event.rentreturn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestLocationPayload;

public class ReturnRequestLocationEvent extends RacEvent<ReturnRequestLocationPayload> {

    @JsonCreator
    public ReturnRequestLocationEvent(@JsonProperty("aggregateId") String aggregateId, @JsonProperty("payload")  ReturnRequestLocationPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_LOCATION.getId();
    }

}
