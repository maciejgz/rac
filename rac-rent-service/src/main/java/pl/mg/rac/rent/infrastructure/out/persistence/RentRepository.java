package pl.mg.rac.rent.infrastructure.out.persistence;

import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.domain.model.Rent;
import pl.mg.rac.rent.infrastructure.out.persistence.entity.mapper.RentEntityMapper;

import java.util.Optional;

public class RentRepository implements RentDatabase {

    private final RentJpaRepository rentJpaRepository;

    public RentRepository(RentJpaRepository rentJpaRepository) {
        this.rentJpaRepository = rentJpaRepository;
    }

    @Override
    public Rent save(Rent rent) {
        return RentEntityMapper.ofEntity(rentJpaRepository.save(RentEntityMapper.ofAggregate(rent)));
    }

    @Override
    public Optional<Rent> findById(String rentId) {
        return rentJpaRepository.findByRentId(rentId).map(RentEntityMapper::ofEntity);
    }


}
