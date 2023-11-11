package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarRentSuccessPayload;

public class CarRentSuccessEvent extends RacEvent<CarRentSuccessPayload> {

    public CarRentSuccessEvent(String aggregateId, CarRentSuccessPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RENT_SUCCESS.getId();
    }

}
