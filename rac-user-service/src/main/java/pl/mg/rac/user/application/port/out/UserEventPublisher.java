package pl.mg.rac.user.application.port.out;

import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedUserEvent;
import pl.mg.rac.commons.event.rent.RentRequestCarEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedUserEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestCarEvent;
import pl.mg.rac.commons.event.user.UserChargedEvent;
import pl.mg.rac.commons.event.user.UserCreatedEvent;
import pl.mg.rac.commons.event.user.UserDeletedEvent;

public interface UserEventPublisher {

    void publishUserEvent(RacEvent<?> event);

    void publishUserCreatedEvent(UserCreatedEvent event);

    void publishUserDeletedEvent(UserDeletedEvent event);

    void publishUserChargedEvent(UserChargedEvent event);

    void publishRentRequestCarEvent(RentRequestCarEvent event);

    void publishRentFailedUserEvent(RentFailedUserEvent event);

    void publishReturnRequestCarEvent(ReturnRequestCarEvent event);
    void publishReturnFailedUserEvent(ReturnFailedUserEvent event);
}
