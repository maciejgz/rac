package pl.mg.rac.commons.event.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarReturnFailedNotExistsPayload;

public class CarReturnFailedNotExistsEvent extends RacEvent<CarReturnFailedNotExistsPayload> {
    @JsonCreator
    public CarReturnFailedNotExistsEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                                         @JsonProperty("payload") CarReturnFailedNotExistsPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RETURN_FAILED_NOT_EXIST.getId();
    }

}
