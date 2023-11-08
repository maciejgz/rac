package pl.mg.rac.user.infrastructure.out.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.mg.rac.user.domain.model.User;

import java.math.BigDecimal;

@Document(collection = "user")
@Data
public class UserEntity {

    @Id
    private String id;
    private String name;
    private BigDecimal balance;

    public static UserEntity ofUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setBalance(user.getBalance());
        return userEntity;
    }
}