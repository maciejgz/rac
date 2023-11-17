package pl.mg.rac.location.infrastructure.out.persistence;

import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.domain.model.CarLocation;

public class CarLocationRepository implements CarLocationDatabase {
    private final CarLocationJpaRepository carRepository;

    public CarLocationRepository(CarLocationJpaRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarLocation saveCarLocation(String vin, CarLocation carLocation) {
        //TODO implement
        return null;
    }
}
