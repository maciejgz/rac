package pl.mg.rac.commons.event.rent;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentFailedUserPayload;

public class RentFailedUserEvent extends RacEvent<RentFailedUserPayload> {

    public RentFailedUserEvent(String aggregateId, RentFailedUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_USER.getId();
    }

}
