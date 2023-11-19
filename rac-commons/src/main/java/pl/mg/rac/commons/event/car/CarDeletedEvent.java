package pl.mg.rac.commons.event.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarDeletedPayload;

public class CarDeletedEvent extends RacEvent<CarDeletedPayload> {
    @JsonCreator
    public CarDeletedEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                           @JsonProperty("payload") CarDeletedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_DELETED.getId();
    }

}
