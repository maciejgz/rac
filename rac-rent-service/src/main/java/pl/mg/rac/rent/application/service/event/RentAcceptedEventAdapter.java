package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentConfirmationEvent;
import pl.mg.rac.rent.application.port.in.AcceptRent;
import pl.mg.rac.rent.application.port.out.RentDatabase;

@Slf4j
public class RentAcceptedEventAdapter implements EventAdapter<RacEvent<?>>, AcceptRent {

    private final RentDatabase rentDatabase;

    public RentAcceptedEventAdapter(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentConfirmationEvent rentConfirmationEvent = (RentConfirmationEvent) event;
        log.debug("RentAcceptedEventAdapter.handle() called with: event = [" + event + "]");
        try {
            rentDatabase.findById(rentConfirmationEvent.getPayload().rentId())
                    .ifPresent(rent -> {
                        rent.acceptRent(rentConfirmationEvent.getPayload().startLocation());
                        rentDatabase.save(rent);
                    });
            //TODO return response to user over websocket
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            //TODO return response to user over websocket
        }
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_CONFIRMATION.name();
    }
}
