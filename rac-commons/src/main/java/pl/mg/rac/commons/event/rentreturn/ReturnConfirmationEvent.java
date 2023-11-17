package pl.mg.rac.commons.event.rentreturn;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnConfirmationPayload;

public class ReturnConfirmationEvent extends RacEvent<ReturnConfirmationPayload> {

    public ReturnConfirmationEvent(String aggregateId, ReturnConfirmationPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_CONFIRMATION.getId();
    }

}
