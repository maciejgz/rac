package pl.mg.rac.user.application.port.out;

import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.UserChargedEvent;
import pl.mg.rac.commons.event.user.UserCreatedEvent;
import pl.mg.rac.commons.event.user.UserDeletedEvent;

public interface UserEventPublisher {

    void publishUserEvent(RacEvent<?> event);

    void publishUserCreatedEvent(UserCreatedEvent event);

    void publishUserDeletedEvent(UserDeletedEvent event);

    void publishUserChargedEvent(UserChargedEvent event);
}
