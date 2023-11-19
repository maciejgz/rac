package pl.mg.rac.location.infrastructure.out.persistence;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import pl.mg.rac.location.infrastructure.out.persistence.entity.UserLocationEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserLocationJpaRepository extends CassandraRepository<UserLocationEntity, UUID> {

    @Query(value = "SELECT * FROM user_location WHERE username = ?0 ORDER BY timestamp DESC LIMIT 1")
    Optional<UserLocationEntity> findFirstByKeyUsernameOrderByKeyTimestampDesc(String username);
}
