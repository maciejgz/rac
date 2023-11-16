package pl.mg.rac.rent.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedCarEvent;
import pl.mg.rac.commons.event.rent.RentFailedUserEvent;
import pl.mg.rac.rent.application.port.in.FailedRentCar;
import pl.mg.rac.rent.application.port.out.RentDatabase;

@Slf4j
public class RentFailedCarEventAdapter implements EventAdapter<RacEvent<?>>, FailedRentCar {

    private final RentDatabase rentDatabase;

    public RentFailedCarEventAdapter(RentDatabase rentDatabase) {
        this.rentDatabase = rentDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentFailedCarEvent rentFailedCarEvent = (RentFailedCarEvent) event;
        log.debug("RentFailedCarEventAdapter.handle() called with: event = [" + event + "]");
        rentDatabase.findById(rentFailedCarEvent.getPayload().rentId())
                .ifPresent(rent -> {
                    rent.declineRent(rentFailedCarEvent.getPayload().errorCode() + ":" + rentFailedCarEvent.getPayload().errorMessage());
                    rentDatabase.save(rent);
                });
        //TODO return response to user over websocket
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_FAILED_CAR.name();
    }
}
