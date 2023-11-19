package pl.mg.rac.rent.infrastructure.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.rent.domain.model.RentStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "rent")
@Data
@AllArgsConstructor
public class RentEntity {

    @Id
    private String id;
    private String rentId;
    private String username;
    private String vin;
    private Location startLocation;
    private BigDecimal distanceTraveled;
    private Location endLocation;
    private Instant rentRequestTimestamp;
    private Instant rentStartTimestamp;
    private Instant returnRequestTimestamp;
    private Instant rentEndTimestamp;
    private RentStatus status;
    private String statusReason;

}
