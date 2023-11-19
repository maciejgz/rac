package pl.mg.rac.commons.event.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.payload.UserDeletedPayload;

public class UserDeletedEvent extends RacEvent<UserDeletedPayload> {

    @JsonCreator
    public UserDeletedEvent(@JsonProperty("aggregateId") String aggregateId, @JsonProperty("payload") UserDeletedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_USER_DELETED.getId();
    }
}

