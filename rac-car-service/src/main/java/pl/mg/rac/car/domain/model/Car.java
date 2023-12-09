package pl.mg.rac.car.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Transient;
import pl.mg.rac.car.domain.exception.CarBrokenException;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.event.car.CarRentSuccessEvent;
import pl.mg.rac.commons.event.car.payload.CarRentSuccessPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Car {

    private String id;
    private String vin;
    private Location location;
    private BigDecimal mileage;
    private String rentalId;
    private Boolean failure = false;
    private String failureReason;


    @Transient
    private final List<RacEvent<? extends RacEventPayload>> events = new ArrayList<>();

    public static Car create(String vin, BigDecimal mileage, Location location) {
        return new Car(vin, location, mileage, null);
    }

    private Car() {

    }

    public Car(String id, String vin, Location location,  BigDecimal mileage, String rentalId, Boolean failure, String failureReason) {
        this.id = id;
        this.vin = vin;
        this.location = location;
        this.mileage = mileage;
        this.rentalId = rentalId;
        this.failure = failure;
        this.failureReason = failureReason;
    }

    public Car(String vin, Location location, BigDecimal mileage, String rentalId) {
        this.vin = vin;
        this.location = location;
        this.mileage = mileage;
        this.rentalId = rentalId;
    }

    public void returnCarRequest() throws CarBrokenException {
        if (failure) {
            throw new CarBrokenException("Car with vin: " + vin + " is broken. Reason: " + failureReason);
        }
    }

    public void returnCar() {
        this.rentalId = null;
    }

    public void rentRequest() throws CarBrokenException {
        if (this.failure) {
            throw new CarBrokenException("Car with vin: " + vin + " is broken. Reason: " + failureReason);
        }
    }

    public void rentCar(String rentalId) {
        this.rentalId = rentalId;
        this.addEvent(new CarRentSuccessEvent(vin, new CarRentSuccessPayload(vin, rentalId)));
    }

    public void addEvent(RacEvent<?> event) {
        events.add(event);
    }

    public void addDistanceTraveled(BigDecimal distanceTraveled) {
        this.mileage = distanceTraveled.add(this.mileage);
    }

    public void updateLocation(Location location) {
        this.location = location;
    }

    public void setFailure(String failureReason) {
        this.failure = true;
        this.failureReason = failureReason;
    }

    public void removeFailure() {
        this.failure = false;
        this.failureReason = null;
    }

}
