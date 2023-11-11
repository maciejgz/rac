package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarRentFailedNotExistsPayload;

public class CarRentFailedNotExistsEvent extends RacEvent<CarRentFailedNotExistsPayload> {

    public CarRentFailedNotExistsEvent(String aggregateId, CarRentFailedNotExistsPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RENT_FAILED_NOT_EXISTS.getId();
    }

}
