package pl.mg.rac.location.infrastructure.out.persistence;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.mg.rac.location.infrastructure.out.persistence.entity.CarLocationEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface CarLocationJpaRepository extends CassandraRepository<CarLocationEntity, UUID> {
    List<CarLocationEntity> findByVinAndTimestampBetween(String vin, Instant from, Instant to);

    CarLocationEntity findFirstByVinOrderByTimestampDesc(String vin);
}
