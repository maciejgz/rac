package pl.mg.rac.car.infrastructure.out.persistence;

import pl.mg.rac.car.application.port.out.CarDatabase;

public class CarRepository implements CarDatabase {

    private final CarJpaRepository carJpaRepository;

    public CarRepository(CarJpaRepository carJpaRepository) {
        this.carJpaRepository = carJpaRepository;
    }
}
