package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarDeletedPayload;
import pl.mg.rac.commons.event.car.payload.CarReturnFailedAlreadyReturnedPayload;

public class CarReturnFailedAlreadyReturnedEvent extends RacEvent<CarReturnFailedAlreadyReturnedPayload> {

    public CarReturnFailedAlreadyReturnedEvent(String aggregateId, CarReturnFailedAlreadyReturnedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_RETURN_FAILED_ALREADY_RETURNED.getId();
    }

}
