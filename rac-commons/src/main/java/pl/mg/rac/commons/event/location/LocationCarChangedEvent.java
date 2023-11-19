package pl.mg.rac.commons.event.location;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.location.payload.LocationCarChangedPayload;

public class LocationCarChangedEvent extends RacEvent<LocationCarChangedPayload> {

    @JsonCreator
    public LocationCarChangedEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                                   @JsonProperty("payload") LocationCarChangedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_LOCATION_CAR_CHANGED.getId();
    }

}
