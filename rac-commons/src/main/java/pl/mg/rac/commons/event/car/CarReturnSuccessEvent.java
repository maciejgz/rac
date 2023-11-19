package pl.mg.rac.commons.event.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarReturnSuccessPayload;

public class CarReturnSuccessEvent extends RacEvent<CarReturnSuccessPayload> {
    @JsonCreator
    public CarReturnSuccessEvent(@JsonProperty(value = "aggregateId") String aggregateId, @JsonProperty("payload") CarReturnSuccessPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RETURN_SUCCESS.getId();
    }

}
