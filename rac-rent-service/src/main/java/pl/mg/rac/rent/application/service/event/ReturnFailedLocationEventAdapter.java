package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedCarEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedLocationEvent;
import pl.mg.rac.rent.application.port.in.FailedReturnCar;
import pl.mg.rac.rent.application.port.in.FailedReturnLocation;

@Slf4j
public class ReturnFailedLocationEventAdapter implements EventAdapter<RacEvent<?>>, FailedReturnLocation {

    @Override
    public void handle(RacEvent<?> event) {
        ReturnFailedLocationEvent returnFailedCarEvent = (ReturnFailedLocationEvent) event;
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_FAILED_LOCATION.name();
    }

}
