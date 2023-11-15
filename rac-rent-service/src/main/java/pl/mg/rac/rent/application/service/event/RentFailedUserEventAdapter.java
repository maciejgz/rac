package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedUserEvent;
import pl.mg.rac.rent.application.port.in.FailedRentUser;

@Slf4j
public class RentFailedUserEventAdapter implements EventAdapter<RacEvent<?>>, FailedRentUser {

    @Override
    public void handle(RacEvent<?> event) {
        RentFailedUserEvent rentFailedUserEvent = (RentFailedUserEvent) event;
        //TODO implement
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_USER.name();
    }
}
