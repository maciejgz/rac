package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedUserEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestCarEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestUserEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnFailedUserPayload;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestCarPayload;
import pl.mg.rac.user.application.port.in.RentRequestUserPort;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;
import pl.mg.rac.user.domain.exception.UserBlockedException;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Slf4j
public class ReturnRequestUserAdapter implements EventAdapter<RacEvent<?>>, RentRequestUserPort {

    private final UserDatabase userDatabase;
    private final UserEventPublisher userEventPublisher;

    public ReturnRequestUserAdapter(UserDatabase userDatabase, UserEventPublisher userEventPublisher) {
        this.userDatabase = userDatabase;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnRequestUserEvent returnRequestUserEvent = (ReturnRequestUserEvent) event;
        log.debug("ReturnRequestUserAdapter.handle() called with: event = [" + event + "]");
        Optional<User> user = userDatabase.findByName(returnRequestUserEvent.getPayload().username());
        if (user.isPresent()) {
            try {
                BigDecimal chargedAmount = user.get().finishRentAndCharge(returnRequestUserEvent.getPayload().rentStartDate(),
                        Instant.now(),
                        returnRequestUserEvent.getPayload().distanceTraveled());
                userDatabase.save(user.get());
                pushReturnRequestCarEvent(returnRequestUserEvent, chargedAmount);
                log.debug("User: {} charged with: {}", user.get().getName(), chargedAmount);
            } catch (UserBlockedException e) {
                pushReturnFailedUserEvent(returnRequestUserEvent, "USER_BLOCKED", "User blocked. Cannot finish rent. Contact with support.");
            }
        } else {
            pushReturnFailedUserEvent(returnRequestUserEvent, "USER_NOT_FOUND", "User not found");
        }
    }

    private void pushReturnRequestCarEvent(ReturnRequestUserEvent returnRequestUserEvent, BigDecimal chargedAmount) {
        ReturnRequestCarEvent eventToPush = new ReturnRequestCarEvent(
                returnRequestUserEvent.getPayload().rentId(),
                new ReturnRequestCarPayload(
                        returnRequestUserEvent.getPayload().rentId(),
                        returnRequestUserEvent.getPayload().username(),
                        returnRequestUserEvent.getPayload().vin(),
                        returnRequestUserEvent.getPayload().distanceTraveled(),
                        returnRequestUserEvent.getPayload().rentStartDate(),
                        chargedAmount,
                        returnRequestUserEvent.getPayload().endLocation()
                )
        );
        userEventPublisher.publishReturnRequestCarEvent(eventToPush);
    }

    private void pushReturnFailedUserEvent(ReturnRequestUserEvent returnRequestUserEvent, String USER_BLOCKED, String errorMessage) {
        userEventPublisher.publishReturnFailedUserEvent(new ReturnFailedUserEvent(
                returnRequestUserEvent.getPayload().rentId(),
                new ReturnFailedUserPayload(
                        returnRequestUserEvent.getPayload().rentId(),
                        returnRequestUserEvent.getPayload().username(),
                        returnRequestUserEvent.getPayload().vin(),
                        USER_BLOCKED,
                        errorMessage
                )
        ));
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_USER.name();
    }
}
