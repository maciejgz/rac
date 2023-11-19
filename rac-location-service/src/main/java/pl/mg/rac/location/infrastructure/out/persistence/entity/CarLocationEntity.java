package pl.mg.rac.location.infrastructure.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table(value = "car_location")
@AllArgsConstructor
@Getter
public class CarLocationEntity {

    @PrimaryKeyClass
    @AllArgsConstructor
    @Getter
    public static class CarKey {

        @PrimaryKeyColumn(name = "vin", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String vin;

        @PrimaryKeyColumn(name = "timestamp", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
        private Instant timestamp;
    }

    @PrimaryKey
    private CarKey key;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
