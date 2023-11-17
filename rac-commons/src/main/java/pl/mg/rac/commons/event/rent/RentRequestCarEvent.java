package pl.mg.rac.commons.event.rent;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentRequestCarPayload;

public class RentRequestCarEvent extends RacEvent<RentRequestCarPayload> {

    public RentRequestCarEvent(String aggregateId, RentRequestCarPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_REQUEST_CAR.getId();
    }

}
