package pl.mg.rac.car.application.service.event;

import pl.mg.rac.commons.event.RacEvent;

public interface EventAdapter<T extends RacEvent<?>> {

    void handle(T event);

    String getEventType();

}
