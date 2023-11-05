package pl.mg.rac.car.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.mg.rac.commons.value.Location;

@Document
@Getter
public class Car {

    @Id
    private String id;
    private String vin;
    private String plateNumber;
    private Location location;
    private Boolean rented;
    private Double mileage;
    private String lastRentalId;

}
