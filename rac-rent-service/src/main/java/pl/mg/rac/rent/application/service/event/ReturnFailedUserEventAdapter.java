package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedUserEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnUser;

@Slf4j
public class ReturnFailedUserEventAdapter implements EventAdapter<ReturnFailedUserEvent>, FailedReturnUser {

    @Override
    public void handle(ReturnFailedUserEvent event) {
        log.debug("handle() called with: event = [" + event + "]");
        //TODO implement
    }
}
