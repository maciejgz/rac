package pl.mg.rac.car.application.port.out;

import pl.mg.rac.commons.event.RacEvent;

public interface CarEventPublisher {


    void publishCarEvent(RacEvent<?> event);
}
