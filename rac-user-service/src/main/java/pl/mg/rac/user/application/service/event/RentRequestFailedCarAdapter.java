package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedCarEvent;
import pl.mg.rac.user.application.port.in.RentRequestUserPort;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

@Slf4j
public class RentRequestFailedCarAdapter implements EventAdapter<RacEvent<?>>, RentRequestUserPort {

    private final UserDatabase userDatabase;

    public RentRequestFailedCarAdapter(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentFailedCarEvent rentRequestUserEvent = (RentFailedCarEvent) event;
        log.debug("RentRequestFailedCarAdapter.handle() called with: event = [" + event + "]");
        Optional<User> user = userDatabase.findByName(rentRequestUserEvent.getPayload().username());
        if (user.isPresent()) {
            user.ifPresent(User::cancelRent);
            userDatabase.save(user.get());
        }
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_CAR.name();
    }
}
