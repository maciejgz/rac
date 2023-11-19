package pl.mg.rac.commons.event.rentreturn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnFailedCarPayload;

public class ReturnFailedCarEvent extends RacEvent<ReturnFailedCarPayload> {

    @JsonCreator
    public ReturnFailedCarEvent(@JsonProperty("aggregateId") String aggregateId,@JsonProperty("payload")  ReturnFailedCarPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_CAR.getId();
    }

}
