package pl.mg.rac.car.infrastructure.out.persistence.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.mg.rac.commons.value.Location;

@Document(collection = "car")
@Data
public class CarEntity {

    private String id;
    private String vin;
    private Location location;
    private Boolean rented;
    private Double mileage;
    private String lastRentalId;

}
