package pl.mg.rac.location.infrastructure.out.persistence.entity;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "\"car-location\"")
public class CarLocationEntity {

    @PrimaryKey
    private String id;

}
