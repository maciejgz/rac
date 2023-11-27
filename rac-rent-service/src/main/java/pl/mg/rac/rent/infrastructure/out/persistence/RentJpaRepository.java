package pl.mg.rac.rent.infrastructure.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.mg.rac.rent.domain.model.RentStatus;
import pl.mg.rac.rent.infrastructure.out.persistence.entity.RentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentJpaRepository extends MongoRepository<RentEntity, String> {

    Optional<RentEntity> findByRentId(String rentId);

    List<RentEntity> findByStatus(RentStatus status);

}
