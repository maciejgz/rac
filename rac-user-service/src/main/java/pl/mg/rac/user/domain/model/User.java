package pl.mg.rac.user.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Getter
public class User {

    @Id
    private String id;
    private String name;
    private BigDecimal balance;

    private User() {
    }

    public User(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public BigDecimal charge(BigDecimal amount) {
        balance = balance.add(amount);
        return balance;
    }

}
