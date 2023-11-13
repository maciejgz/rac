package pl.mg.rac.commons.event.rentreturn;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnFailedCarPayload;

public class ReturnFailedCarEvent extends RacEvent<ReturnFailedCarPayload> {

    public ReturnFailedCarEvent(String aggregateId, ReturnFailedCarPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_CAR.getId();
    }

}
