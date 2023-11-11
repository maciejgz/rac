package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarReturnSuccessPayload;

public class CarReturnSuccessEvent extends RacEvent<CarReturnSuccessPayload> {

    public CarReturnSuccessEvent(String aggregateId, CarReturnSuccessPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RETURN_SUCCESS.getId();
    }

}
