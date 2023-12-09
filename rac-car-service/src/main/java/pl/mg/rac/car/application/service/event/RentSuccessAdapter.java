package pl.mg.rac.car.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rent.RentSuccessEvent;

import java.util.Optional;

@Slf4j
public class RentSuccessAdapter implements EventAdapter<RacEvent<?>> {

    private final CarDatabase carDatabase;

    public RentSuccessAdapter(CarDatabase carDatabase) {
        this.carDatabase = carDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        RentSuccessEvent rentSuccessEvent = (RentSuccessEvent) event;
        Optional<Car> car = carDatabase.getCarByVin(rentSuccessEvent.getPayload().vin());
        if (car.isPresent()) {
            car.get().rentCar(rentSuccessEvent.getPayload().rentId());
            carDatabase.save(car.get());
        } else {
            log.warn("Car with VIN: {} not found", rentSuccessEvent.getPayload().vin());
        }
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RENT_SUCCESS.name();
    }
}
