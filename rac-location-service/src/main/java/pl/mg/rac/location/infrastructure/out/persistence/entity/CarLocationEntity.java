package pl.mg.rac.location.infrastructure.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(value = "car_location")
@AllArgsConstructor
@Getter
public class CarLocationEntity {

    @PrimaryKey
    private UUID id;
    private String vin;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Instant timestamp;



}
