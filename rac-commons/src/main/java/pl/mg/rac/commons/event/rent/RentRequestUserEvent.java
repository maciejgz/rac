package pl.mg.rac.commons.event.rent;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentRequestUserPayload;

public class RentRequestUserEvent extends RacEvent<RentRequestUserPayload> {

    public RentRequestUserEvent(String aggregateId, RentRequestUserPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_REQUEST_USER.getId();
    }

}
