package pl.mg.rac.commons.event.car;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.car.payload.CarCreatedPayload;

public class CarCreatedEvent extends RacEvent<CarCreatedPayload> {

    public CarCreatedEvent(String aggregateId, CarCreatedPayload payload) {
        super(aggregateId, payload);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_CAR_CREATED.getId();
    }

}
