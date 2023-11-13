package pl.mg.rac.rent.infrastructure.out.persistence;

import pl.mg.rac.rent.application.port.out.RentDatabase;

public class RentOrderRepository implements RentDatabase {

    private final RentOrderJpaRepository rentOrderJpaRepository;

    public RentOrderRepository(RentOrderJpaRepository rentOrderJpaRepository) {
        this.rentOrderJpaRepository = rentOrderJpaRepository;
    }
}
