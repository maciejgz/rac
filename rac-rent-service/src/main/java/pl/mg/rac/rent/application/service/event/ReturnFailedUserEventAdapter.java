package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedUserEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnUser;
import pl.mg.rac.rent.application.port.out.RentDatabase;

@Slf4j
public class ReturnFailedUserEventAdapter implements EventAdapter<RacEvent<?>>, FailedReturnUser {


    private final RentDatabase rentDatabase;

    public ReturnFailedUserEventAdapter(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnFailedUserEvent returnFailedUserEvent = (ReturnFailedUserEvent) event;
        log.debug("ReturnFailedUserEventAdapter.handle() called with: event = [" + event + "]");
        rentDatabase.findById(returnFailedUserEvent.getPayload().rentId())
                .ifPresent(rent -> {
                    rent.declineReturn(returnFailedUserEvent.getPayload().errorCode() + ":" + returnFailedUserEvent.getPayload().errorMessage());
                    rentDatabase.save(rent);
                });
        // TODO return response to user over websocket
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_USER.name();
    }
}
