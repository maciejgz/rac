package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnConfirmationEvent;
import pl.mg.rac.rent.application.port.in.AcceptReturn;

@Slf4j
public class ReturnAcceptedEventAdapter implements EventAdapter<RacEvent<?>>, AcceptReturn {

    @Override
    public void handle(RacEvent<?> event) {
        ReturnConfirmationEvent returnConfirmationEvent = (ReturnConfirmationEvent) event;
        log.debug("handle() called with: event = [" + event + "]");
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_CONFIRMATION.name();
    }
}
