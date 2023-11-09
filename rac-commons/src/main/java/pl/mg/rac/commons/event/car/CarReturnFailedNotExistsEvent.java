package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarDeletedPayload;
import pl.mg.rac.commons.event.car.payload.CarReturnFailedNotExistsPayload;

public class CarReturnFailedNotExistsEvent extends RacEvent<CarReturnFailedNotExistsPayload> {

    public CarReturnFailedNotExistsEvent(String aggregateId, CarReturnFailedNotExistsPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RETURN_FAILED_NOT_EXIST.getId();
    }

}
