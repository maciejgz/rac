package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rent.RentConfirmationEvent;
import pl.mg.rac.rent.application.port.in.AcceptRent;

@Slf4j
public class RentAcceptedEventAdapter implements EventAdapter<RentConfirmationEvent>, AcceptRent {

    @Override
    public void handle(RentConfirmationEvent event) {
        log.debug("handle() called with: event = [" + event + "]");
        //TODO implement
    }
}
