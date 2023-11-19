package pl.mg.rac.commons.event.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.payload.UserCreatedPayload;

public class UserCreatedEvent extends RacEvent<UserCreatedPayload> {

    @JsonCreator
    public UserCreatedEvent(@JsonProperty("aggregateId") String aggregateId,
                            @JsonProperty("payload") UserCreatedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_USER_CREATED.getId();
    }

}
