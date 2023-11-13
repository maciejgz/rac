package pl.mg.rac.rent.infrastructure.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.mg.rac.rent.infrastructure.out.persistence.entity.RentEntity;

@Repository
public interface RentJpaRepository extends MongoRepository<RentEntity, String> {


}
