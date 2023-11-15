package pl.mg.rac.rent.application.service.event;

import pl.mg.rac.commons.event.RacEvent;

public interface EventAdapter<T extends RacEvent<?>> {

    void handle(T event);

}
