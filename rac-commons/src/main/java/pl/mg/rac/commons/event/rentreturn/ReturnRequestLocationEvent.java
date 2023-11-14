package pl.mg.rac.commons.event.rentreturn;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestLocationPayload;

public class ReturnRequestLocationEvent extends RacEvent<ReturnRequestLocationPayload> {

    public ReturnRequestLocationEvent(String aggregateId, ReturnRequestLocationPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_LOCATION.getId();
    }

}
