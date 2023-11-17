package pl.mg.rac.commons.event.location;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.location.payload.LocationCarChangedPayload;

public class LocationCarChangedEvent extends RacEvent<LocationCarChangedPayload> {

    public LocationCarChangedEvent(String aggregateId, LocationCarChangedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_LOCATION_CAR_CHANGED.getId();
    }

}
