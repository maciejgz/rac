package pl.mg.rac.rent.infrastructure.out.persistence;

import pl.mg.rac.rent.application.port.out.RentDatabase;
import pl.mg.rac.rent.domain.model.Rent;
import pl.mg.rac.rent.domain.model.RentStatus;
import pl.mg.rac.rent.infrastructure.out.persistence.entity.mapper.RentEntityMapper;

import java.util.List;
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

    @Override
    public List<Rent> findOpenRentRequests() {
        return rentJpaRepository.findByStatus(RentStatus.RENT_REQUESTED).stream().map(RentEntityMapper::ofEntity).toList();
    }

    @Override
    public List<Rent> findOpenReturnRequests() {
        return rentJpaRepository.findByStatus(RentStatus.RETURN_REQUESTED).stream().map(RentEntityMapper::ofEntity).toList();
    }

    @Override
    public List<Rent> findDeclinedReturnRequests() {
        return rentJpaRepository.findByStatus(RentStatus.RETURN_DECLINED).stream().map(RentEntityMapper::ofEntity).toList();
    }

    @Override
    public Optional<Rent> findOpenRentRequestByUserAndVin(String username, String vin) {
        return rentJpaRepository.findByUsernameAndVinAndStatusIn(username, vin, List.of(RentStatus.RENT_REQUESTED, RentStatus.RENT_ACCEPTED))
                .map(RentEntityMapper::ofEntity);
    }

    @Override
    public Optional<Rent> findOpenRentRequestByUser(String username) {
        return rentJpaRepository.findByUsernameAndStatusIn(username, List.of(RentStatus.RENT_REQUESTED, RentStatus.RENT_ACCEPTED))
                .map(RentEntityMapper::ofEntity);
    }

    @Override
    public Optional<Rent> findOpenRentRequestByVin(String vin) {
        return rentJpaRepository.findByVinAndStatusIn(vin, List.of(RentStatus.RENT_REQUESTED, RentStatus.RENT_ACCEPTED))
                .map(RentEntityMapper::ofEntity);
    }

}
