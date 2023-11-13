package pl.mg.rac.commons.event.rent;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.payload.RentConfirmationPayload;

public class RentConfirmationEvent extends RacEvent<RentConfirmationPayload> {

    public RentConfirmationEvent(String aggregateId, RentConfirmationPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_CONFIRMATION.getId();
    }

}
