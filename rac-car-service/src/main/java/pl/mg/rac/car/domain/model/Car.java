package pl.mg.rac.car.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Transient;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.event.RacEventPayload;
import pl.mg.rac.commons.value.Location;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Car {

    private String id;
    private String vin;
    private Location location;
    private Boolean rented;
    private Double mileage;
    private String rentalId;

    @Transient
    private final List<RacEvent<? extends RacEventPayload>> events = new ArrayList<>();

    public static Car create(String vin, Double mileage, Location location) {
        //TODO add validation
        return new Car(vin, location, false, mileage, null);
    }

    private Car() {

    }

    public Car(String id, String vin, Location location, Boolean rented, Double mileage, String rentalId) {
        this.id = id;
        this.vin = vin;
        this.location = location;
        this.rented = rented;
        this.mileage = mileage;
        this.rentalId = rentalId;
    }

    public Car(String vin, Location location, Boolean rented, Double mileage, String rentalId) {
        this.vin = vin;
        this.location = location;
        this.rented = rented;
        this.mileage = mileage;
        this.rentalId = rentalId;
    }

    public void returnCar(String rentalId, Double mileage, Location location) {
    }

    public void rentCar(String rentalId) {
    }

    public void addEvent(RacEvent<?> event) {
        events.add(event);
    }

    public void updateMileage(Double mileage) {
        this.mileage = mileage;
    }

    public void updateLocation(Location location) {
        this.location = location;
    }

}
