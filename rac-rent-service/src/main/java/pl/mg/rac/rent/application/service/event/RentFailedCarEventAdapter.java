package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedCarEvent;
import pl.mg.rac.rent.application.port.in.FailedRentCar;

@Slf4j
public class RentFailedCarEventAdapter implements EventAdapter<RacEvent<?>>, FailedRentCar {

    @Override
    public void handle(RacEvent<?> event) {
        RentFailedCarEvent rentFailedCarEvent = (RentFailedCarEvent) event;
        //TODO implement
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_CAR.name();
    }
}
