package pl.mg.rac.car.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.mg.rac.commons.event.RacEvent;
import pl.mg.rac.commons.value.Location;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
public class Car {

    @Id
    private String id;
    private String vin;
    private Location location;
    private Boolean rented;
    private Double mileage;
    private String lastRentalId;

    @Transient
    private final List<RacEvent<?>> events = new ArrayList<>();

    public static Car create(String vin, Double mileage, Location location) {
        //TODO add validation
        return new Car(vin, location, false, mileage, null);
    }

    private Car() {

    }

    public Car(String vin, Location location, Boolean rented, Double mileage, String lastRentalId) {
        this.vin = vin;
        this.location = location;
        this.rented = rented;
        this.mileage = mileage;
        this.lastRentalId = lastRentalId;
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
