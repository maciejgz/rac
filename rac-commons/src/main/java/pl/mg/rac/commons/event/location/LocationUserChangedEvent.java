package pl.mg.rac.commons.event.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.location.payload.LocationUserChangedPayload;

public class LocationUserChangedEvent extends RacEvent<LocationUserChangedPayload> {

    @JsonCreator
    public LocationUserChangedEvent(@JsonProperty("aggregateId") String aggregateId, @JsonProperty("payload") LocationUserChangedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_LOCATION_USER_CHANGED.getId();
    }

}
