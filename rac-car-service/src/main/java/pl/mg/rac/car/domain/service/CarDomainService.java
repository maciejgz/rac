package pl.mg.rac.car.domain.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.value.Location;

@Slf4j
public class CarDomainService {

    public Car addCar(String vin, Double mileage, Location location) {
        log.debug("Adding car with vin: {}", vin);
        Car car = new Car(vin, location, false, mileage, null);
        //TODO add event
        return car;
    }


}
