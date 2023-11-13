package pl.mg.rac.rent.infrastructure.out.persistence;

import pl.mg.rac.rent.application.port.out.RentDatabase;

public class RentRepository implements RentDatabase {

    private final RentJpaRepository rentrJpaRepository;

    public RentRepository(RentJpaRepository rentJpaRepository) {
        this.rentrJpaRepository = rentJpaRepository;
    }
}
