package pl.mg.rac.commons.event.rentreturn;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.location.payload.LocationCarChangedPayload;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestUserPayload;

public class ReturnRequestUserEvent extends RacEvent<ReturnRequestUserPayload> {

    public ReturnRequestUserEvent(String aggregateId, ReturnRequestUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_USER.getId();
    }

}
