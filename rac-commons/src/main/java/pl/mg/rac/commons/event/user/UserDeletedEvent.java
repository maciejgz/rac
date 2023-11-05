package pl.mg.rac.commons.event.user;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.payload.UserDeletedPayload;

public class UserDeletedEvent extends RacEvent<UserDeletedPayload> {

    public UserDeletedEvent(String aggregateId, UserDeletedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_USER_DELETED.getId();
    }
}

