package pl.mg.rac.commons.event.user;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.payload.UserChargedPayload;

public class UserChargedEvent extends RacEvent<UserChargedPayload> {

    public UserChargedEvent(String aggregateId, UserChargedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_USER_CHARGED.getId();
    }

}
