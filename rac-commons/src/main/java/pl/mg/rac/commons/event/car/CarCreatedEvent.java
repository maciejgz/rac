package pl.mg.rac.commons.event.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarCreatedPayload;

public class CarCreatedEvent extends RacEvent<CarCreatedPayload> {
    @JsonCreator
    public CarCreatedEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                           @JsonProperty("payload") CarCreatedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_CREATED.getId();
    }

}
