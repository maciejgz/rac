package pl.mg.rac.location.infrastructure.out.persistence;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.mg.rac.location.infrastructure.out.persistence.entity.CarLocationEntity;

public interface CarLocationJpaRepository extends CassandraRepository<CarLocationEntity, String> {
}
