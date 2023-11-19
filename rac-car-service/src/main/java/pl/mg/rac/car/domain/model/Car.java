package pl.mg.rac.car.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Transient;
import pl.mg.rac.car.domain.exception.CarAlreadyRentedException;
import pl.mg.rac.car.domain.exception.CarAlreadyReturnedException;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.event.car.CarRentSuccessEvent;
import pl.mg.rac.commons.event.car.CarReturnSuccessEvent;
import pl.mg.rac.commons.event.car.payload.CarRentSuccessPayload;
import pl.mg.rac.commons.event.car.payload.CarReturnSuccessPayload;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Car {

    private String id;
    private String vin;
    private Location location;
    private Boolean rented;
    private BigDecimal mileage;
    private String rentalId;

    @Transient
    private final List<RacEvent<? extends RacEventPayload>> events = new ArrayList<>();

    public static Car create(String vin, BigDecimal mileage, Location location) {
        //TODO add validation
        return new Car(vin, location, false, mileage, null);
    }

    private Car() {

    }

    public Car(String id, String vin, Location location, Boolean rented, BigDecimal mileage, String rentalId) {
        this.id = id;
        this.vin = vin;
        this.location = location;
        this.rented = rented;
        this.mileage = mileage;
        this.rentalId = rentalId;
    }

    public Car(String vin, Location location, Boolean rented, BigDecimal mileage, String rentalId) {
        this.vin = vin;
        this.location = location;
        this.rented = rented;
        this.mileage = mileage;
        this.rentalId = rentalId;
    }

    public void returnCar(String rentalId, BigDecimal distanceTraveled) throws CarAlreadyReturnedException {
        if (!rented) {
            throw new CarAlreadyReturnedException("Car with vin: " + vin + " is already returned. Last rental id: " + rentalId);
        }
        this.rented = false;
        this.rentalId = null;
        this.addEvent(new CarReturnSuccessEvent(vin, new CarReturnSuccessPayload(vin, rentalId, location, distanceTraveled, mileage)));
    }

    public void rentCar(String rentalId) throws CarAlreadyRentedException {
        if (rented) {
            throw new CarAlreadyRentedException("Car with vin: " + vin + " is already rented. Rental id: " + rentalId);
        }
        this.rented = true;
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

}
