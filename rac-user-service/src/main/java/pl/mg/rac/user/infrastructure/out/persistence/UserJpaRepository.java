package pl.mg.rac.user.infrastructure.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.mg.rac.user.domain.model.User;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends MongoRepository<User, String> {

    boolean existsByName(String name);

    Optional<User> findByName(String name);

}
