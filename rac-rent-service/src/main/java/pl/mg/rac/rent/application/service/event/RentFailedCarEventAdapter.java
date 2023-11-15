package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rent.RentFailedCarEvent;
import pl.mg.rac.rent.application.port.in.FailedRentCar;

@Slf4j
public class RentFailedCarEventAdapter implements EventAdapter<RentFailedCarEvent>, FailedRentCar {

    @Override
    public void handle(RentFailedCarEvent event) {
        log.debug("handle() called with: event = [" + event + "]");
        //TODO implement
    }
}
