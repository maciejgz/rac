package pl.mg.rac.commons.event.rentreturn;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnFailedUserPayload;

public class ReturnFailedUserEvent extends RacEvent<ReturnFailedUserPayload> {

    public ReturnFailedUserEvent(String aggregateId, ReturnFailedUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_USER.getId();
    }

}
