package pl.mg.rac.car.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.application.port.out.CarEventPublisher;
import pl.mg.rac.car.domain.exception.CarBrokenException;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnConfirmationEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnFailedCarEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnRequestCarEvent;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnConfirmationPayload;
import pl.mg.rac.commons.event.rentreturn.payload.ReturnFailedCarPayload;

import java.util.Optional;

@Slf4j
public class ReturnRequestCarAdapter implements EventAdapter<RacEvent<?>> {

    private final CarDatabase carDatabase;
    private final CarEventPublisher eventPublisher;

    public ReturnRequestCarAdapter(CarDatabase carDatabase, CarEventPublisher eventPublisher) {
        this.carDatabase = carDatabase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnRequestCarEvent returnRequestCarEvent = (ReturnRequestCarEvent) event;
        Optional<Car> car = carDatabase.getCarByVin(returnRequestCarEvent.getPayload().vin());
        if (car.isPresent()) {
            try {
                car.get().returnCarRequest();
                carDatabase.save(car.get());
                car.get().getEvents().forEach(eventPublisher::publishCarEvent);
                publishReturnConfirmationEvent(returnRequestCarEvent, car.get());
            } catch (CarBrokenException e) {
                String message = "Car with VIN: " + returnRequestCarEvent.getPayload().vin()
                        + " is broken and cannot be returned. Reason: " + e.getMessage() + ". Contact with support.";
                log.error(message);
                pushReturnFailedCarBrokenEvent(returnRequestCarEvent, message);
            }
        } else {
            String message = "Car with VIN: " + returnRequestCarEvent.getPayload().vin() + " not found";
            log.warn(message);
            publishCarNotExistsEvent(returnRequestCarEvent, message);
            pushReturnFailedCarNotExistEvent(returnRequestCarEvent, message);
        }
    }

    private void pushReturnFailedCarBrokenEvent(ReturnRequestCarEvent returnRequestCarEvent, String message) {
        eventPublisher.publishRentEvent(new ReturnFailedCarEvent(
                returnRequestCarEvent.getAggregateId(),
                new ReturnFailedCarPayload(
                        returnRequestCarEvent.getPayload().rentId(),
                        returnRequestCarEvent.getPayload().username(),
                        returnRequestCarEvent.getPayload().vin(),
                        returnRequestCarEvent.getPayload().endLocation(),
                        returnRequestCarEvent.getPayload().distanceTraveled(),
                        returnRequestCarEvent.getPayload().chargedAmount(),
                        "CAR_BROKEN",
                        message
                )
        ));
    }

    private void pushReturnFailedCarNotExistEvent(ReturnRequestCarEvent returnRequestCarEvent, String message) {
        eventPublisher.publishRentEvent(new ReturnFailedCarEvent(
                returnRequestCarEvent.getAggregateId(),
                new ReturnFailedCarPayload(
                        returnRequestCarEvent.getPayload().rentId(),
                        returnRequestCarEvent.getPayload().username(),
                        returnRequestCarEvent.getPayload().vin(),
                        returnRequestCarEvent.getPayload().endLocation(),
                        returnRequestCarEvent.getPayload().distanceTraveled(),
                        returnRequestCarEvent.getPayload().chargedAmount(),
                        "CAR_NOT_EXISTS",
                        message
                )
        ));
    }

    private void publishCarNotExistsEvent(ReturnRequestCarEvent returnRequestCarEvent, String message) {
        eventPublisher.publishCarEvent(new ReturnFailedCarEvent(
                returnRequestCarEvent.getAggregateId(),
                new ReturnFailedCarPayload(
                        returnRequestCarEvent.getPayload().rentId(),
                        returnRequestCarEvent.getPayload().username(),
                        returnRequestCarEvent.getPayload().vin(),
                        null,
                        returnRequestCarEvent.getPayload().distanceTraveled(),
                        returnRequestCarEvent.getPayload().chargedAmount(),
                        "RAC_CAR_NOT_EXISTS",
                        message
                )
        ));
    }

    private void publishReturnConfirmationEvent(ReturnRequestCarEvent returnRequestCarEvent, Car car) {
        eventPublisher.publishRentEvent(new ReturnConfirmationEvent(
                returnRequestCarEvent.getAggregateId(),
                new ReturnConfirmationPayload(
                        returnRequestCarEvent.getPayload().rentId(),
                        returnRequestCarEvent.getPayload().username(),
                        returnRequestCarEvent.getPayload().vin(),
                        returnRequestCarEvent.getPayload().endLocation(),
                        returnRequestCarEvent.getPayload().distanceTraveled(),
                        returnRequestCarEvent.getPayload().chargedAmount()
                )
        ));
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_REQUEST_CAR.name();
    }
}
