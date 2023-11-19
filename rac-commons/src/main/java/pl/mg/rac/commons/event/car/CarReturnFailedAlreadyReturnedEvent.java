package pl.mg.rac.commons.event.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarReturnFailedAlreadyReturnedPayload;

public class CarReturnFailedAlreadyReturnedEvent extends RacEvent<CarReturnFailedAlreadyReturnedPayload> {
    @JsonCreator
    public CarReturnFailedAlreadyReturnedEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                                               @JsonProperty("payload") CarReturnFailedAlreadyReturnedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RETURN_FAILED_ALREADY_RETURNED.getId();
    }

}
