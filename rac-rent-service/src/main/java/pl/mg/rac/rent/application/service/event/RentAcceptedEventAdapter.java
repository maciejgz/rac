package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentConfirmationEvent;
import pl.mg.rac.commons.event.rent.RentSuccessEvent;
import pl.mg.rac.commons.event.rent.payload.RentSuccessPayload;
import pl.mg.rac.rent.application.port.in.AcceptRent;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.domain.model.Rent;

import java.util.Optional;

@Slf4j
public class RentAcceptedEventAdapter implements EventAdapter<RacEvent<?>>, AcceptRent {

    private final RentDatabase rentDatabase;
    private final RentEventPublisher rentEventPublisher;

    public RentAcceptedEventAdapter(RentDatabase rentDatabase, RentEventPublisher rentEventPublisher) {
        this.rentDatabase = rentDatabase;
        this.rentEventPublisher = rentEventPublisher;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentConfirmationEvent rentConfirmationEvent = (RentConfirmationEvent) event;
        log.debug("RentAcceptedEventAdapter.handle() called with: event = [" + event + "]");
        try {
            Optional<Rent> rent = rentDatabase.findById(rentConfirmationEvent.getPayload().rentId());
            if (rent.isPresent()) {
                rent.get().acceptRent(rentConfirmationEvent.getPayload().startLocation());
                rentDatabase.save(rent.get());
                rentEventPublisher.publishRentEvent(new RentSuccessEvent(
                        rentConfirmationEvent.getAggregateId(),
                        new RentSuccessPayload(
                                rentConfirmationEvent.getAggregateId(),
                                rent.get().getUsername(),
                                rent.get().getVin()
                        )
                ));
                //TODO return response to user over websocket
            } else {
                log.warn("Rent with id: {} not found", rentConfirmationEvent.getPayload().rentId());
                //TODO return response to user over websocket
            }
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
