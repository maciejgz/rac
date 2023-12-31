package pl.mg.rac.car.infrastructure.out.persistence;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.mg.rac.car.infrastructure.out.persistence.entity.CarEntity;

import java.util.Optional;

@Repository
public interface CarJpaRepository extends MongoRepository<CarEntity, String> {

    boolean existsByVin(String vin);

    Optional<CarEntity> findByVin(String vin);

    void deleteByVin(String vin);

    @Aggregation(pipeline = {
            "{ $sample: { size: 1 } }"
    })
    Optional<CarEntity> getRandomCar();
}
