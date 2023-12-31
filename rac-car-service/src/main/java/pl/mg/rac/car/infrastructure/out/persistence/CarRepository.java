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

    @Override
    public boolean existsByVin(String vin) {
        return carJpaRepository.existsByVin(vin);
    }

    @Override
    public Optional<Car> getCarByVin(String vin) {
        return carJpaRepository.findByVin(vin).map(this::mapToAggregate);
    }

    @Override
    public Car save(Car car) {
        return this.mapToAggregate(carJpaRepository.save(CarEntity.ofCar(car)));
    }

    @Override
    public void deleteByVin(String vin) {
        carJpaRepository.deleteByVin(vin);
    }

    @Override
    public Optional<Car> getRandomCar() {
        return carJpaRepository.getRandomCar().map(this::mapToAggregate);
    }

    private Car mapToAggregate(CarEntity carEntity) {
        return new Car(carEntity.getId(), carEntity.getVin(), carEntity.getLocation(),
                carEntity.getMileage(), carEntity.getRentalId(), carEntity.getFailure(), carEntity.getFailureReason());
    }
}
