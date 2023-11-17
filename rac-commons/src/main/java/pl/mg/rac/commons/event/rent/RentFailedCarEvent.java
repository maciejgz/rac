package pl.mg.rac.commons.event.rent;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentFailedCarPayload;

public class RentFailedCarEvent extends RacEvent<RentFailedCarPayload> {

    public RentFailedCarEvent(String aggregateId, RentFailedCarPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_CAR.getId();
    }

}
