package pl.mg.rac.rent.infrastructure.out.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rent")
public class RentEntity {

    @Id
    private String id;

}
