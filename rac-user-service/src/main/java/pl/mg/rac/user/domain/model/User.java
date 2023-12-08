package pl.mg.rac.user.domain.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Transient;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.event.user.UserChargedEvent;
import pl.mg.rac.commons.event.user.payload.UserChargedPayload;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.user.domain.model.policy.ChargeUserPolicyRegistry;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class User {

    private String id;
    private String name;
    private BigDecimal balance;
    private Location location;
    //TODO make it read only cache value filled in when rent-service accepts rent and cleared when rent-service returns is committed
    private String currentRentId;
    private boolean blocked = false;
    private LocalDate registrationDate;


    @Transient
    private final List<RacEvent<? extends RacEventPayload>> events;

    private User() {
        this.events = new ArrayList<>();
    }

    public User(String id, String name, BigDecimal balance, Location location, String currentRentId, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.location = location;
        this.events = new ArrayList<>();
        this.registrationDate = registrationDate;
        this.currentRentId = currentRentId;
    }

    public User(String name, BigDecimal balance, Location location, LocalDate registrationDate) {
        this.name = name;
        this.balance = balance;
        this.location = location;
        this.registrationDate = registrationDate;
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

    public BigDecimal finishRentAndCharge(Instant rentStartDate, Instant rentEndDate, BigDecimal distanceTraveled) {
        //TODO add policy to calculate rent amount
        BigDecimal amount = calculateRentCharge(rentStartDate, rentEndDate, distanceTraveled);
        charge(amount);
        cancelRent();
        return amount;
    }

    public void rollbackRentReturn(String rentId, BigDecimal amount) {
        log.debug("rollbackRentReturn() called with: rentId = [" + rentId + "], amount = [" + amount + "]");
        this.currentRentId = rentId;
        this.balance = balance.subtract(amount);
    }

    private BigDecimal calculateRentCharge(Instant rentStartDate, Instant rentEndDate, BigDecimal distanceTraveled) {
        return ChargeUserPolicyRegistry.getChargeUserPolicy(this).calculateRentCharge(rentStartDate, rentEndDate, distanceTraveled);
    }

    public void startRent(String rentId) {
        if (this.currentRentId != null && !this.currentRentId.isEmpty()) {
            throw new IllegalStateException("User already has a rent" + rentId);
        }
        this.currentRentId = rentId;
    }

    public void block() {
        this.blocked = true;
    }

    public void unblock() {
        this.blocked = false;
    }

    public void cancelRent() {
        this.currentRentId = null;
    }

}
