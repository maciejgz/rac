package pl.mg.rac.rent.infrastructure.out.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rent_order")
public class RentOrderEntity {

    @Id
    private String id;

}
