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

@Table(value = "user_location")
@Getter
@AllArgsConstructor
public class UserLocationEntity {


    @PrimaryKeyClass
    @AllArgsConstructor
    @Getter
    public static class UserKey {

        @PrimaryKeyColumn(name = "username", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String username;

        @PrimaryKeyColumn(name = "timestamp", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
        private Instant timestamp;
    }

    @PrimaryKey
    private UserLocationEntity.UserKey key;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
