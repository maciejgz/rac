package pl.mg.rac.commons.event.user;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.payload.UserCreatedPayload;

public class UserCreatedEvent extends RacEvent<UserCreatedPayload> {

    public UserCreatedEvent(String aggregateId, UserCreatedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_USER_CREATED.getId();
    }

}
