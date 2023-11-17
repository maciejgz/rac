package pl.mg.rac.commons.event.rentreturn;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestCarPayload;

public class ReturnRequestCarEvent extends RacEvent<ReturnRequestCarPayload> {

    public ReturnRequestCarEvent(String aggregateId, ReturnRequestCarPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_CAR.getId();
    }

}
