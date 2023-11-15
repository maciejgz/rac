package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedCarEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnCar;

@Slf4j
public class ReturnFailedCarEventAdapter implements EventAdapter<RacEvent<?>>, FailedReturnCar {

    @Override
    public void handle(RacEvent<?> event) {
        ReturnFailedCarEvent returnFailedCarEvent = (ReturnFailedCarEvent) event;
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_CAR.name();
    }




}
