package pl.mg.rac.car.application.service.event;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.event.EventType;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.rentreturn.ReturnSuccessEvent;

import java.util.Optional;

@Slf4j
public class ReturnSuccessAdapter implements EventAdapter<RacEvent<?>> {

    private final CarDatabase carDatabase;

    public ReturnSuccessAdapter(CarDatabase carDatabase) {
        this.carDatabase = carDatabase;
    }

    @Override
    public void handle(RacEvent<?> event) {
        ReturnSuccessEvent returnSuccessEvent = (ReturnSuccessEvent) event;
        Optional<Car> car = carDatabase.getCarByVin(returnSuccessEvent.getPayload().vin());
        if (car.isPresent()) {
            car.get().returnCar();
            carDatabase.save(car.get());
        } else {
            log.warn("Car with VIN: {} not found", returnSuccessEvent.getPayload().vin());
        }
    }

    @Override
    public String getEventType() {
        return EventType.RAC_RETURN_SUCCESS.name();
    }
}
