package pl.mg.rac.location.application.service.event;

import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestLocationEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestUserEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnRequestUserPayload;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.application.port.out.LocationEventPublisher;
import pl.mg.rac.location.domain.model.CarLocation;
import pl.mg.rac.location.domain.service.CarLocationDomainService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ReturnLocationEventAdapter implements EventAdapter<RacEvent<?>> {

    private final CarLocationDatabase carLocationDatabase;
    private final LocationEventPublisher eventPublisher;
    private final CarLocationDomainService carLocationDomainService;


    public ReturnLocationEventAdapter(CarLocationDatabase carLocationDatabase, LocationEventPublisher eventPublisher,
                                      CarLocationDomainService carLocationDomainService) {
        this.carLocationDatabase = carLocationDatabase;
        this.eventPublisher = eventPublisher;
        this.carLocationDomainService = carLocationDomainService;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnRequestLocationEvent eventMapped = (ReturnRequestLocationEvent) event;
        List<CarLocation> locations = carLocationDatabase.findLocationBetween(eventMapped.getPayload().vin(), eventMapped.getPayload().rentStartDate(),
                Instant.now());
        BigDecimal distanceTraveled = carLocationDomainService.calculateDistanceTraveled(locations);
        ReturnRequestUserEvent returnRequestUserEvent = new ReturnRequestUserEvent(eventMapped.getPayload().rentId(),
                new ReturnRequestUserPayload(
                        eventMapped.getPayload().rentId(),
                        eventMapped.getPayload().username(),
                        eventMapped.getPayload().vin(),
                        distanceTraveled,
                        eventMapped.getPayload().rentStartDate(),
                        locations.getLast().getLocation()
                ));
        eventPublisher.publishRentEvent(returnRequestUserEvent);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_LOCATION.name();
    }
}
