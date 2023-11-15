package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedCarEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnCar;

@Slf4j
public class ReturnFailedCarEventAdapter implements EventAdapter<ReturnFailedCarEvent>, FailedReturnCar {

    @Override
    public void handle(ReturnFailedCarEvent event) {
        log.debug("handle() called with: event = [" + event + "]");
        //TODO implement
    }
}
