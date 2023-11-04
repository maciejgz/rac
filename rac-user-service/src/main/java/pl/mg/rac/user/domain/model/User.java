package pl.mg.rac.user.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class User {

    private String name;
    private BigDecimal balance;

    protected User() {
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
