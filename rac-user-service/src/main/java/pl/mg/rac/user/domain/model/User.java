package pl.mg.rac.user.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.user.UserChargedEvent;
import pl.mg.rac.commons.event.user.payload.UserChargedPayload;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
public class User {

    @Id
    private String id;
    private String name;
    private BigDecimal balance;
    @Transient
    private final List<RacEvent<?>> events;

    private User() {
        this.events = new ArrayList<>();
    }

    public User(String id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.events = new ArrayList<>();
    }

    public User(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        this.events = new ArrayList<>();
    }

    public void addEvent(RacEvent<?> event) {
        events.add(event);
    }

    public BigDecimal charge(BigDecimal amount) {
        addEvent(new UserChargedEvent(name, new UserChargedPayload(name, balance, balance.add(amount))));
        balance = balance.add(amount);
        return balance;
    }

}
