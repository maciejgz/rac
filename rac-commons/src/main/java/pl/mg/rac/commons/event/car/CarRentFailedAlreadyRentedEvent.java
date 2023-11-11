package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarRentFailedAlreadyRentedPayload;

public class CarRentFailedAlreadyRentedEvent extends RacEvent<CarRentFailedAlreadyRentedPayload> {

    public CarRentFailedAlreadyRentedEvent(String aggregateId, CarRentFailedAlreadyRentedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RENT_FAILED_ALREADY_RENTED.getId();
    }

}
