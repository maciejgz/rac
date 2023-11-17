package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedCarEvent;
import pl.mg.rac.user.application.port.in.RentRequestUserPort;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

@Slf4j
public class ReturnRequestFailedCarAdapter implements EventAdapter<RacEvent<?>>, RentRequestUserPort {

    private final UserDatabase userDatabase;

    public ReturnRequestFailedCarAdapter(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnFailedCarEvent returnFailedCarEvent = (ReturnFailedCarEvent) event;
        log.debug("ReturnRequestFailedCarAdapter.handle() called with: event = [" + event + "]");
        Optional<User> user = userDatabase.findByName(returnFailedCarEvent.getPayload().username());
        if (user.isPresent()) {
            user.get().rollbackRentReturn(returnFailedCarEvent.getPayload().rentId(), returnFailedCarEvent.getPayload().chargedAmount());
            userDatabase.save(user.get());
            // TODO make return issue saga as a sequence - here event to rent service should be pushed
        }
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_CAR.name();
    }
}
