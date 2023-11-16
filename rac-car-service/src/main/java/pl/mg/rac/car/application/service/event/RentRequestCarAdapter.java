package pl.mg.rac.car.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.domain.exception.CarAlreadyRentedException;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentFailedCarEvent;
import pl.mg.rac.commons.event.rent.RentRequestCarEvent;
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
                car.get().rentCar(rentRequestCarEvent.getPayload().rentId());
                carDatabase.save(car.get());
                eventPublisher.publishRentEvent(rentRequestCarEvent);
            } catch (CarAlreadyRentedException e) {
                String errorMessage = "rentConfirmationEvent: car " + rentRequestCarEvent.getPayload().vin()
                        + " already has rent " + rentRequestCarEvent.getPayload().rentId();
                log.error(errorMessage, e);
                publishRentFailedEvent(rentRequestCarEvent, errorMessage);
            }
        } else {
            log.warn("Car with VIN: {} not found", rentRequestCarEvent.getPayload().vin());
            publishCarNotFoundEvent(rentRequestCarEvent);
        }
    }

    private void publishRentFailedEvent(RentRequestCarEvent rentRequestCarEvent, String errorMessage) {
        RentFailedCarEvent failEvent = new RentFailedCarEvent(
                rentRequestCarEvent.getPayload().rentId(),
                new RentFailedCarPayload(
                        rentRequestCarEvent.getPayload().rentId(),
                        rentRequestCarEvent.getPayload().username(),
                        rentRequestCarEvent.getPayload().vin(),
                        "CAR_ALREADY_RENTED",
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
