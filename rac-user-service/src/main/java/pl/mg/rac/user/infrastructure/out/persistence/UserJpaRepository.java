package pl.mg.rac.user.infrastructure.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.mg.rac.user.infrastructure.out.persistence.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends MongoRepository<UserEntity, String> {

    boolean existsByName(String name);

    Optional<UserEntity> findByName(String name);

}
