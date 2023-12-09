package pl.mg.rac.user.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnSuccessEvent;
import pl.mg.rac.user.application.port.in.RentSuccessPort;
import pl.mg.rac.user.application.port.out.UserDatabase;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

@Slf4j
public class ReturnSuccessEventAdapter implements EventAdapter<RacEvent<?>>, RentSuccessPort {

    private final UserDatabase userDatabase;

    public ReturnSuccessEventAdapter(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnSuccessEvent returnSuccessEvent = (ReturnSuccessEvent) event;
        log.debug("handle() called with: event = [" + returnSuccessEvent + "]");
        Optional<User> user = userDatabase.findByName(returnSuccessEvent.getPayload().username());
        if (user.isPresent()) {
            user.get().returnSuccess();
            userDatabase.save(user.get());
        } else {
            String errorMessage = "ReturnSuccessEvent: user not found " + returnSuccessEvent.getPayload().username();
            log.error(errorMessage);
        }
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_SUCCESS.name();
    }
}
