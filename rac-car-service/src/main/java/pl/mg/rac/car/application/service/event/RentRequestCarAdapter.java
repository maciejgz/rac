package pl.mg.rac.car.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.domain.exception.CarBrokenException;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentConfirmationEvent;
import pl.mg.rac.commons.event.rent.RentFailedCarEvent;
import pl.mg.rac.commons.event.rent.RentRequestCarEvent;
import pl.mg.rac.commons.event.rent.payload.RentConfirmationPayload;
import pl.mg.rac.commons.event.rent.payload.RentFailedCarPayload;

import java.util.Optional;

@Slf4j
public class RentRequestCarAdapter implements EventAdapter<RacEvent<?>> {

    private final CarDatabase carDatabase;
    private final CarEventPublisher eventPublisher;

    public RentRequestCarAdapter(CarDatabase carDatabase, CarEventPublisher eventPublisher) {
        this.carDatabase = carDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentRequestCarEvent rentRequestCarEvent = (RentRequestCarEvent) event;
        Optional<Car> car = carDatabase.getCarByVin(rentRequestCarEvent.getPayload().vin());
        if (car.isPresent()) {
            try {
                car.get().rentRequest();
                carDatabase.save(car.get());
                publishRentConfirmedEvent(rentRequestCarEvent, car.get());
            } catch (CarBrokenException e) {
                String errorMessage = "Car with VIN: " + rentRequestCarEvent.getPayload().vin() + " is broken: " + e.getMessage();
                log.error(errorMessage, e);
                publishRentFailedCarBrokenEvent(rentRequestCarEvent, errorMessage);
            }
        } else {
            log.warn("Car with VIN: {} not found", rentRequestCarEvent.getPayload().vin());
            publishCarNotFoundEvent(rentRequestCarEvent);
        }
    }

    private void publishRentConfirmedEvent(RentRequestCarEvent rentRequestCarEvent, Car car) {
        eventPublisher.publishRentEvent(
                new RentConfirmationEvent(
                        rentRequestCarEvent.getPayload().rentId(),
                        new RentConfirmationPayload(
                                rentRequestCarEvent.getPayload().rentId(),
                                rentRequestCarEvent.getPayload().username(),
                                rentRequestCarEvent.getPayload().vin(),
                                car.getLocation()
                        ))
        );
    }

    private void publishRentFailedCarBrokenEvent(RentRequestCarEvent rentRequestCarEvent, String errorMessage) {
        RentFailedCarEvent failEvent = new RentFailedCarEvent(
                rentRequestCarEvent.getPayload().rentId(),
                new RentFailedCarPayload(
                        rentRequestCarEvent.getPayload().rentId(),
                        rentRequestCarEvent.getPayload().username(),
                        rentRequestCarEvent.getPayload().vin(),
                        "CAR_BROKEN",
                        errorMessage
                )
        );
        eventPublisher.publishRentEvent(failEvent);
    }

    private void publishCarNotFoundEvent(RentRequestCarEvent rentRequestCarEvent) {
        RentFailedCarEvent failEvent = new RentFailedCarEvent(
                rentRequestCarEvent.getPayload().rentId(),
                new RentFailedCarPayload(
                        rentRequestCarEvent.getPayload().rentId(),
                        rentRequestCarEvent.getPayload().username(),
                        rentRequestCarEvent.getPayload().vin(),
                        "CAR_NOT_FOUND",
                        "Car with VIN: " + rentRequestCarEvent.getPayload().vin() + " not found"
                )
        );
        eventPublisher.publishRentEvent(failEvent);
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_REQUEST_CAR.name();
    }
}
