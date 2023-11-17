package pl.mg.rac.rent.infrastructure.out.persistence.entity.mapper;

import pl.mg.rac.rent.domain.model.Rent;
import pl.mg.rac.rent.infrastructure.out.persistence.entity.RentEntity;

import java.util.ArrayList;

public class RentEntityMapper {

    public static RentEntity ofAggregate(Rent rent) {
        return new RentEntity(
                rent.getId(),
                rent.getRentId(),
                rent.getUsername(),
                rent.getVin(),
                rent.getStartLocation(),
                rent.getDistanceTraveled(),
                rent.getEndLocation(),
                rent.getRentRequestTimestamp(),
                rent.getRentStartTimestamp(),
                rent.getReturnRequestTimestamp(),
                rent.getRentEndTimestamp(),
                rent.getStatus(),
                rent.getStatusReason()
        );
    }

    public static Rent ofEntity(RentEntity entity) {
        return new Rent(
                entity.getId(),
                entity.getRentId(),
                entity.getUsername(),
                entity.getVin(),
                entity.getStartLocation(),
                entity.getDistanceTraveled(),
                entity.getEndLocation(),
                entity.getRentRequestTimestamp(),
                entity.getRentStartTimestamp(),
                entity.getReturnRequestTimestamp(),
                entity.getRentEndTimestamp(),
                entity.getStatus(),
                entity.getStatusReason(),
                new ArrayList<>());
    }

}
