package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedCarEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnCar;
import pl.mg.rac.rent.application.port.out.RentDatabase;

@Slf4j
public class ReturnFailedCarEventAdapter implements EventAdapter<RacEvent<?>>, FailedReturnCar {

    private final RentDatabase rentDatabase;

    public ReturnFailedCarEventAdapter(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnFailedCarEvent returnFailedCarEvent = (ReturnFailedCarEvent) event;
        log.debug("ReturnFailedCarEventAdapter.handle() called with: event = [" + event + "]");
        rentDatabase.findById(returnFailedCarEvent.getPayload().rentId())
                .ifPresent(rent -> {
                    rent.declineReturn(returnFailedCarEvent.getPayload().errorCode() + ":" + returnFailedCarEvent.getPayload().errorMessage());
                    rentDatabase.save(rent);
                });
        // TODO return response to user over websocket
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_CAR.name();
    }




}
