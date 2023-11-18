package pl.mg.rac.location.infrastructure.out.persistence;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import pl.mg.rac.location.infrastructure.out.persistence.entity.CarLocationEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarLocationJpaRepository extends CassandraRepository<CarLocationEntity, UUID> {
    List<CarLocationEntity> findByKeyVinAndKeyTimestampBetween(String vin, Instant from, Instant to);

    @Query(value = "SELECT * FROM car_location WHERE vin = ?0 ORDER BY timestamp DESC LIMIT 1")
    Optional<CarLocationEntity> findFirstByKeyVinOrderByKeyTimestampDesc(String vin);
}
