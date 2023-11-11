package pl.mg.rac.car.infrastructure.out.persistence;

import pl.mg.rac.car.application.port.out.CarDatabase;
import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.car.infrastructure.out.persistence.entity.CarEntity;

import java.util.Optional;

public class CarRepository implements CarDatabase {

    private final CarJpaRepository carJpaRepository;

    public CarRepository(CarJpaRepository carJpaRepository) {
        this.carJpaRepository = carJpaRepository;
    }

    public boolean existsByVin(String vin) {
        return carJpaRepository.existsByVin(vin);
    }

    public Optional<Car> getCarByVib(String vin) {
        return carJpaRepository.findByVin(vin).map(this::mapToAggregate);
    }

    private Car mapToAggregate(CarEntity carEntity) {
        return new Car(carEntity.getId(), carEntity.getVin(), carEntity.getLocation(), carEntity.getRented(), carEntity.getMileage(), carEntity.getRentalId());
    }
}
