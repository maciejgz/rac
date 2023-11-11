package pl.mg.rac.car.application.port.out;

import pl.mg.rac.car.domain.model.Car;

import java.util.Optional;

public interface CarDatabase {

    boolean existsByVin(String vin);

    Optional<Car> getCarByVin(String vin);

    Car save(Car car);

    void deleteByVin(String vin);
}
