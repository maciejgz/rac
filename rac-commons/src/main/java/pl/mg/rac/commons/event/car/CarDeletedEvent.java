package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarDeletedPayload;

public class CarDeletedEvent extends RacEvent<CarDeletedPayload> {

    public CarDeletedEvent(String aggregateId, CarDeletedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_DELETED.getId();
    }

}
