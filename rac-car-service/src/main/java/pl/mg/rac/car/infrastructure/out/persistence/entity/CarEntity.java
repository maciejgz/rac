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
    private BigDecimal mileage;
    @Field("rental_id")
    private String rentalId;
    private Boolean failure;
    @Field("failure_reason")
    private String failureReason;

    public static CarEntity ofCar(pl.mg.rac.car.domain.model.Car car) {
        return new CarEntity(car.getId(), car.getVin(), car.getLocation(), car.getMileage(),
                car.getRentalId(), car.getFailure(), car.getFailureReason());
    }

}
