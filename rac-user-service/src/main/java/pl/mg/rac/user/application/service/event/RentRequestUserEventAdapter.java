package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentRequestUserEvent;
import pl.mg.rac.user.application.port.in.RentRequestUserPort;

@Slf4j
public class RentRequestUserEventAdapter implements EventAdapter<RacEvent<?>>, RentRequestUserPort {

    @Override
    public void handle(RacEvent<?> event) {
        RentRequestUserEvent rentConfirmationEvent = (RentRequestUserEvent) event;
        log.debug("handle() called with: event = [" + event + "]");
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_REQUEST_USER.name();
    }
}
