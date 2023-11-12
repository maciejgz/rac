package pl.mg.rac.user.infrastructure.out.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;

@Document(collection = "user")
@Data
public class UserEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private BigDecimal balance;
    private Location location;

    public static UserEntity ofUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setBalance(user.getBalance());
        userEntity.setLocation(user.getLocation());
        return userEntity;
    }
}
