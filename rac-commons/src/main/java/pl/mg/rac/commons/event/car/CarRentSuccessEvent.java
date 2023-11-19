package pl.mg.rac.commons.event.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarRentSuccessPayload;

public class CarRentSuccessEvent extends RacEvent<CarRentSuccessPayload> {
    @JsonCreator
    public CarRentSuccessEvent(@JsonProperty(value = "aggregateId") String aggregateId, @JsonProperty("payload") CarRentSuccessPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RENT_SUCCESS.getId();
    }

}
