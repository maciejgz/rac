package pl.mg.rac.user.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Transient;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.event.user.UserChargedEvent;
import pl.mg.rac.commons.event.user.payload.UserChargedPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class User {

    private String id;
    private String name;
    private BigDecimal balance;
    private Location location;
    private String currentRentId;

    @Transient
    private final List<RacEvent<? extends RacEventPayload>> events;

    private User() {
        this.events = new ArrayList<>();
    }

    public User(String id, String name, BigDecimal balance, Location location) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.location = location;
        this.events = new ArrayList<>();
    }

    public User(String name, BigDecimal balance, Location location) {
        this.name = name;
        this.balance = balance;
        this.location = location;
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

    public void rentCar(String rentId) {
        if (rentId != null && !rentId.isEmpty()) {
            throw new IllegalStateException("User already has a rent" + rentId);
        }
        this.currentRentId = rentId;
    }

    public void cancelRent() {
        this.currentRentId = null;
    }

}
