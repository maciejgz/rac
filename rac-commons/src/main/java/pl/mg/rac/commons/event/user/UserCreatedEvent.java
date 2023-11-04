package pl.mg.rac.commons.event.user;

import pl.mg.rac.commons.event.RacEvent;


public class UserCreatedEvent extends RacEvent {
    public UserCreatedEvent(String aggregateId) {
        super(aggregateId);
    }

    @Override
    public String getEventType() {
        return null;
    }

    @Override
    public Object getPayload() {
        return null;
    }
}
