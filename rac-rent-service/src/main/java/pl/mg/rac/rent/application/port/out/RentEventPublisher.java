package pl.mg.rac.rent.application.port.out;

import pl.mg.rac.commons.event.RacEvent;

public interface RentEventPublisher {

    public void publishRentEvent(RacEvent<?> racEvent);

}
