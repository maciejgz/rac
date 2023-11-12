package pl.mg.rac.commons.event.location;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.location.payload.LocationUserChangedPayload;

public class LocationUserChangedEvent extends RacEvent<LocationUserChangedPayload> {

    public LocationUserChangedEvent(String aggregateId, LocationUserChangedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_LOCATION_USER_CHANGED.getId();
    }

}
