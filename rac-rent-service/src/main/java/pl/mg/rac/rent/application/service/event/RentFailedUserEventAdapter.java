package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedUserEvent;
import pl.mg.rac.rent.application.port.in.FailedRentUser;
import pl.mg.rac.rent.application.port.out.RentDatabase;

@Slf4j
public class RentFailedUserEventAdapter implements EventAdapter<RacEvent<?>>, FailedRentUser {

    private final RentDatabase rentDatabase;

    public RentFailedUserEventAdapter(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentFailedUserEvent rentFailedUserEvent = (RentFailedUserEvent) event;
        log.debug("RentFailedUserEventAdapter.handle() called with: event = [" + event + "]");
        rentDatabase.findById(rentFailedUserEvent.getPayload().rentId())
                .ifPresent(rent -> {
                    rent.declineRent(rentFailedUserEvent.getPayload().errorCode() + ":" + rentFailedUserEvent.getPayload().errorMessage());
                    rentDatabase.save(rent);
                });
        //TODO return response to user over websocket
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_USER.name();
    }
}
