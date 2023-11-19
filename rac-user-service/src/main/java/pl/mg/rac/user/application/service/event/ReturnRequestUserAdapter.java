package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestCarEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestUserEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestCarPayload;
import pl.mg.rac.user.application.port.in.RentRequestUserPort;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.application.port.out.UserEventPublisher;

import java.math.BigDecimal;
import java.time.Instant;

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
        userDatabase.findByName(returnRequestUserEvent.getPayload().username())
                .ifPresent(user -> {
                    BigDecimal chargedAmount = user.finishRentAndCharge(returnRequestUserEvent.getPayload().rentStartDate(),
                            Instant.now(),
                            returnRequestUserEvent.getPayload().distanceTraveled());
                    userDatabase.save(user);
                    ReturnRequestCarEvent eventToPush = new ReturnRequestCarEvent(
                            returnRequestUserEvent.getPayload().rentId(),
                            new ReturnRequestCarPayload(
                                    returnRequestUserEvent.getPayload().rentId(),
                                    returnRequestUserEvent.getPayload().username(),
                                    returnRequestUserEvent.getPayload().vin(),
                                    returnRequestUserEvent.getPayload().distanceTraveled(),
                                    returnRequestUserEvent.getPayload().rentStartDate(),
                                    chargedAmount
                            )
                    );
                    userEventPublisher.publishReturnRequestCarEvent(eventToPush);
                });
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_USER.name();
    }
}
