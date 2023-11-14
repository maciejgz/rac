package pl.mg.rac.rent.application.service.event;

import pl.mg.rac.commons.event.RacEvent;

public interface EventAdapter {

    void handle(RacEvent<?> event);

}
