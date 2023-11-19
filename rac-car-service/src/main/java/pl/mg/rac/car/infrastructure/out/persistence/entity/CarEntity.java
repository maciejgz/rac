package pl.mg.rac.car.infrastructure.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

@Document(collection = "car")
@Data
@AllArgsConstructor
public class CarEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String vin;
    private Location location;
    private Boolean rented;
    private BigDecimal mileage;
    @Field("rental_id")
    private String rentalId;

    public static CarEntity ofCar(pl.mg.rac.car.domain.model.Car car) {
        return new CarEntity(car.getId(), car.getVin(), car.getLocation(), car.getRented(), car.getMileage(), car.getRentalId());
    }

}
