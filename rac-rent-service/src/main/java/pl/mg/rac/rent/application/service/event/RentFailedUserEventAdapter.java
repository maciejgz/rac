package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rent.RentFailedUserEvent;
import pl.mg.rac.rent.application.port.in.FailedRentUser;

@Slf4j
public class RentFailedUserEventAdapter implements EventAdapter<RentFailedUserEvent>, FailedRentUser {

    @Override
    public void handle(RentFailedUserEvent event) {
        log.debug("handle() called with: event = [" + event + "]");
        //TODO implement
    }
}
