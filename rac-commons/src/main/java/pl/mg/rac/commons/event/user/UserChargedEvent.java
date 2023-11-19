package pl.mg.rac.commons.event.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.payload.UserChargedPayload;

public class UserChargedEvent extends RacEvent<UserChargedPayload> {

    @JsonCreator
    public UserChargedEvent(@JsonProperty("aggregateId") String aggregateId,
                            @JsonProperty("payload") UserChargedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_USER_CHARGED.getId();
    }

}
