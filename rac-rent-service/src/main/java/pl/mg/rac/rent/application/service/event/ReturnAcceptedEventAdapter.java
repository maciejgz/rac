package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnConfirmationEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnSuccessEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnSuccessPayload;
import pl.mg.rac.rent.application.port.in.AcceptReturn;
import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.application.port.out.RentEventPublisher;
import pl.mg.rac.rent.domain.model.Rent;

import java.util.Optional;

@Slf4j
public class ReturnAcceptedEventAdapter implements EventAdapter<RacEvent<?>>, AcceptReturn {

    private final RentDatabase rentDatabase;
    private final RentEventPublisher eventPublisher;

    public ReturnAcceptedEventAdapter(RentDatabase rentDatabase, RentEventPublisher eventPublisher) {
        this.rentDatabase = rentDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnConfirmationEvent returnConfirmationEvent = (ReturnConfirmationEvent) event;
        log.debug("ReturnAcceptedEventAdapter.handle() called with: event = [" + event + "]");
        Optional<Rent> rent = rentDatabase.findById(returnConfirmationEvent.getPayload().rentId());

        if (rent.isPresent()) {
            rent.get().acceptReturn(returnConfirmationEvent.getPayload().endLocation(), returnConfirmationEvent.getPayload().distanceTraveled());
            rentDatabase.save(rent.get());
            pushReturnSuccessEvent(returnConfirmationEvent);
            // TODO return response to user over websocket
        } else {
            log.warn("Rent with id: {} not found", returnConfirmationEvent.getPayload().rentId());
        }
    }

    private void pushReturnSuccessEvent(ReturnConfirmationEvent returnConfirmationEvent) {
        eventPublisher.publishRentEvent(new ReturnSuccessEvent(
                returnConfirmationEvent.getAggregateId(),
                new ReturnSuccessPayload(
                        returnConfirmationEvent.getPayload().rentId(),
                        returnConfirmationEvent.getPayload().username(),
                        returnConfirmationEvent.getPayload().vin(),
                        returnConfirmationEvent.getPayload().endLocation(),
                        returnConfirmationEvent.getPayload().distanceTraveled(),
                        returnConfirmationEvent.getPayload().chargedAmount()
                )
        ));
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_CONFIRMATION.name();
    }
}
