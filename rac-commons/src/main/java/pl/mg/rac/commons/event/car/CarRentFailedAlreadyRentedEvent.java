package pl.mg.rac.commons.event.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarRentFailedAlreadyRentedPayload;

public class CarRentFailedAlreadyRentedEvent extends RacEvent<CarRentFailedAlreadyRentedPayload> {
    @JsonCreator
    public CarRentFailedAlreadyRentedEvent(@JsonProperty(value = "aggregateId") String aggregateId,
                                           @JsonProperty("payload")CarRentFailedAlreadyRentedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RENT_FAILED_ALREADY_RENTED.getId();
    }

}
