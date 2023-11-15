package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedUserEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnUser;

@Slf4j
public class ReturnFailedUserEventAdapter implements EventAdapter<RacEvent<?>>, FailedReturnUser {


    @Override
    public void handle(RacEvent<?> event) {
        ReturnFailedUserEvent returnFailedUserEvent = (ReturnFailedUserEvent) event;
        //TODO implement
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_USER.name();
    }
}
