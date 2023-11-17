package pl.mg.rac.rent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.value.Location;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

    private String id;
    private String rentId;
    private String username;
    private String vin;
    private Location startLocation;
    //TODO replace with BigDecimal
    private double distanceTraveled;
    private Location endLocation;
    private Instant rentRequestTimestamp;
    private Instant rentStartTimestamp;
    private Instant returnRequestTimestamp;
    private Instant rentEndTimestamp;
    private RentStatus status;
    private String statusReason;

    private List<RacEvent<?>> events = new ArrayList<>();

    public static Rent requestRent(String username, String vin, Location location) {
        Rent rent = new Rent();
        rent.moveToRentRequestedStatus();
        rent.rentId = UUID.randomUUID().toString();
        rent.username = username;
        rent.vin = vin;
        rent.startLocation = location;
        rent.distanceTraveled = 0;
        rent.rentRequestTimestamp = Instant.now();
        return rent;
    }

    public void acceptRent(Location startLocation) {
        this.moveToRentAcceptedStatus();
        this.startLocation = startLocation;
        this.rentStartTimestamp = Instant.now();
    }

    public void declineRent(String reason) {
        this.moveToRentDeclinedStatus(reason);
        this.rentEndTimestamp = Instant.now();
    }

    public void requestReturn() {
        this.moveToReturnRequestedStatus();
        this.returnRequestTimestamp = Instant.now();
    }

    public void acceptReturn(Location location, double distanceTraveled) {
        this.moveToReturnAcceptedStatus(location);
        this.distanceTraveled = distanceTraveled;
        this.rentEndTimestamp = Instant.now();
    }

    public void declineReturn(String reason) {
        this.moveToReturnDeclinedStatus();
        this.rentEndTimestamp = Instant.now();
        this.statusReason = reason;
    }

    // state machine
    public void moveToRentRequestedStatus() {
        if (this.status != null) {
            throw new IllegalStateException("Rent already has a status");
        }
        this.status = RentStatus.RENT_REQUESTED;
    }

    public void moveToRentAcceptedStatus() {
        if (this.status == null || this.status != RentStatus.RENT_REQUESTED) {
            throw new IllegalStateException("Rent status is not RENT_ACCEPTED");
        }
        this.status = RentStatus.RENT_ACCEPTED;
    }

    public void moveToRentDeclinedStatus(String reason) {
        if (this.status == null || this.status != RentStatus.RENT_REQUESTED) {
            throw new IllegalStateException("Rent status is not RENT_DECLINED");
        }
        this.statusReason = reason;
        this.status = RentStatus.RENT_DECLINED;
    }

    public void moveToReturnRequestedStatus() {
        if (this.status == null || this.status != RentStatus.RENT_ACCEPTED) {
            throw new IllegalStateException("Rent status is not RENT_ACCEPTED");
        }
        this.status = RentStatus.RETURN_REQUESTED;
    }

    public void moveToReturnAcceptedStatus(Location location) {
        if (this.status == null || this.status != RentStatus.RETURN_REQUESTED) {
            throw new IllegalStateException("Rent status is not RETURN_REQUESTED");
        }
        this.endLocation = location;
        this.status = RentStatus.RETURN_ACCEPTED;
    }

    public void moveToReturnDeclinedStatus() {
        if (this.status == null || this.status != RentStatus.RETURN_REQUESTED) {
            throw new IllegalStateException("Rent status is not RETURN_REQUESTED");
        }
        this.status = RentStatus.RETURN_DECLINED;
    }

    public void addDomainEvent(RacEvent<?> event) {
        this.events.add(event);
    }

}
