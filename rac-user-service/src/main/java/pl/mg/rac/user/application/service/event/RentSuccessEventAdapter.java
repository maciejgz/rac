package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentSuccessEvent;
import pl.mg.rac.user.application.port.in.RentSuccessPort;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

@Slf4j
public class RentSuccessEventAdapter implements EventAdapter<RacEvent<?>>, RentSuccessPort {

    private final UserDatabase userDatabase;

    public RentSuccessEventAdapter(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentSuccessEvent rentSuccessEvent = (RentSuccessEvent) event;
        log.debug("handle() called with: event = [" + rentSuccessEvent + "]");
        Optional<User> user = userDatabase.findByName(rentSuccessEvent.getPayload().username());
        if (user.isPresent()) {
            user.get().startRent(rentSuccessEvent.getPayload().rentId());
            userDatabase.save(user.get());
        } else {
            String errorMessage = "rentConfirmationEvent: user not found " + rentSuccessEvent.getPayload().username();
            log.error(errorMessage);
        }
    }


    @Override
    public String getEventType() {
        return EventType.RAC_RENT_SUCCESS.name();
    }
}
