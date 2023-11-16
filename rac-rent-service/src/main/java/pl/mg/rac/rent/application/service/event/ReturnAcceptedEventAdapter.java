package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnConfirmationEvent;
import pl.mg.rac.rent.application.port.in.AcceptReturn;
import pl.mg.rac.rent.application.port.out.RentDatabase;

@Slf4j
public class ReturnAcceptedEventAdapter implements EventAdapter<RacEvent<?>>, AcceptReturn {

    private final RentDatabase rentDatabase;

    public ReturnAcceptedEventAdapter(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnConfirmationEvent returnConfirmationEvent = (ReturnConfirmationEvent) event;
        log.debug("ReturnAcceptedEventAdapter.handle() called with: event = [" + event + "]");
        rentDatabase.findById(returnConfirmationEvent.getPayload().rentId())
                .ifPresent(rent -> {
                    rent.acceptReturn(returnConfirmationEvent.getPayload().endLocation(), returnConfirmationEvent.getPayload().distanceTraveled());
                    rentDatabase.save(rent);
                });
        // TODO return response to user over websocket
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_CONFIRMATION.name();
    }
}
