package pl.mg.rac.location.application.port.out;

import pl.mg.rac.commons.event.RacEvent;

public interface LocationEventPublisher {

    public void publishRentEvent(RacEvent<?> racEvent);

}
