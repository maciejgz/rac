package pl.mg.rac.commons.event.rentreturn;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnFailedLocationPayload;

public class ReturnFailedLocationEvent extends RacEvent<ReturnFailedLocationPayload> {

    public ReturnFailedLocationEvent(String aggregateId, ReturnFailedLocationPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_LOCATION.getId();
    }

}
