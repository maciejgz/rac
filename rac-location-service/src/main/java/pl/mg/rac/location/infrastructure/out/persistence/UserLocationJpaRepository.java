package pl.mg.rac.location.infrastructure.out.persistence;

import org.springframework.data.cassandra.repository.CassandraRepository;
import pl.mg.rac.location.infrastructure.out.persistence.entity.UserLocationEntity;

public interface UserLocationJpaRepository extends CassandraRepository<UserLocationEntity, String> {
}
