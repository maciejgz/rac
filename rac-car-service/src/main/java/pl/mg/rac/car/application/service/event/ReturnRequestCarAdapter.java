package pl.mg.rac.car.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestCarEvent;

@Slf4j
public class ReturnRequestCarAdapter implements EventAdapter<RacEvent<?>> {

    private final CarDatabase carDatabase;
    private final CarEventPublisher eventPublisher;

    public ReturnRequestCarAdapter(CarDatabase carDatabase, CarEventPublisher eventPublisher) {
        this.carDatabase = carDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnRequestCarEvent returnRequestCarEvent = (ReturnRequestCarEvent) event;
        //TODO implement
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_CAR.name();
    }
}
