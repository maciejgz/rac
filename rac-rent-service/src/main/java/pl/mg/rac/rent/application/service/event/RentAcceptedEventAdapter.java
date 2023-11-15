package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentConfirmationEvent;
import pl.mg.rac.rent.application.port.in.AcceptRent;

@Slf4j
public class RentAcceptedEventAdapter implements EventAdapter<RacEvent<?>>, AcceptRent {

    @Override
    public void handle(RacEvent<?> event) {
        RentConfirmationEvent rentConfirmationEvent = (RentConfirmationEvent) event;
        log.debug("handle() called with: event = [" + event + "]");
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_CONFIRMATION.name();
    }
}
