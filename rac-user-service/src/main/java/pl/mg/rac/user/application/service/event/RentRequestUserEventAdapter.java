package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedUserEvent;
import pl.mg.rac.commons.event.rent.RentRequestCarEvent;
import pl.mg.rac.commons.event.rent.RentRequestUserEvent;
import pl.mg.rac.commons.event.rent.payload.RentFailedUserPayload;
import pl.mg.rac.commons.event.rent.payload.RentRequestCarPayload;
import pl.mg.rac.user.application.port.in.RentRequestUserPort;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

@Slf4j
public class RentRequestUserEventAdapter implements EventAdapter<RacEvent<?>>, RentRequestUserPort {

    private final UserDatabase userDatabase;
    private final UserEventPublisher userEventPublisher;

    public RentRequestUserEventAdapter(UserDatabase userDatabase, UserEventPublisher userEventPublisher) {
        this.userDatabase = userDatabase;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentRequestUserEvent rentRequestUserEvent = (RentRequestUserEvent) event;
        log.debug("handle() called with: event = [" + event + "]");
        Optional<User> user = userDatabase.findByName(rentRequestUserEvent.getPayload().username());
        if (user.isPresent()) {
            try {
                user.get().rentCar(rentRequestUserEvent.getPayload().rentId());
                userDatabase.save(user.get());
                pushRentRequestCarEvent(rentRequestUserEvent);
            } catch (IllegalStateException e) {
                String errorMessage = "rentConfirmationEvent: user " + rentRequestUserEvent.getPayload().username()
                        + " already has rent " + rentRequestUserEvent.getPayload().rentId();
                log.error(errorMessage, e);
                pushRentFailedEvent(rentRequestUserEvent, "USER_ALREADY_HAS_RENT", errorMessage);
            }
        } else {
            String errorMessage = "rentConfirmationEvent: rentId " + rentRequestUserEvent.getPayload().rentId()
                    + " user not found " + rentRequestUserEvent.getPayload().username();
            log.error(errorMessage);
            pushRentFailedEvent(rentRequestUserEvent, "USER_NOT_FOUND", errorMessage);
        }
    }

    private void pushRentRequestCarEvent(RentRequestUserEvent rentRequestUserEvent) {
        userEventPublisher.publishRentRequestCarEvent(new RentRequestCarEvent(rentRequestUserEvent.getPayload().rentId(),
                new RentRequestCarPayload(
                        rentRequestUserEvent.getPayload().rentId(),
                        rentRequestUserEvent.getPayload().username(),
                        rentRequestUserEvent.getPayload().vin()
                )
        ));
    }

    private void pushRentFailedEvent(RentRequestUserEvent rentRequestUserEvent, String USER_NOT_FOUND, String errorMessage) {
        userEventPublisher.publishRentFailedUserEvent(new RentFailedUserEvent(rentRequestUserEvent.getPayload().rentId(),
                new RentFailedUserPayload(
                        rentRequestUserEvent.getPayload().rentId(),
                        rentRequestUserEvent.getPayload().username(),
                        rentRequestUserEvent.getPayload().vin(),
                        USER_NOT_FOUND,
                        errorMessage
                )
        ));
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_REQUEST_USER.name();
    }
}
