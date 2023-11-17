package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedLocationEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnLocation;
import pl.mg.rac.rent.application.port.out.RentDatabase;

@Slf4j
public class ReturnFailedLocationEventAdapter implements EventAdapter<RacEvent<?>>, FailedReturnLocation {

    private final RentDatabase rentDatabase;

    public ReturnFailedLocationEventAdapter(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnFailedLocationEvent returnFailedCarEvent = (ReturnFailedLocationEvent) event;
        log.debug("ReturnFailedLocationEventAdapter.handle() called with: event = [" + event + "]");
        rentDatabase.findById(returnFailedCarEvent.getPayload().rentId())
                .ifPresent(rent -> {
                    rent.declineReturn(returnFailedCarEvent.getPayload().errorCode() + ":" + returnFailedCarEvent.getPayload().errorMessage());
                    rentDatabase.save(rent);
                });
        // TODO return response to user over websocket
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_LOCATION.name();
    }

}
