package pl.mg.rac.rent.application.port.out;

import pl.mg.rac.rent.domain.model.Rent;

import java.util.Optional;

public interface RentDatabase {
    Rent save(Rent rent);

    Optional<Rent> findById(String rentId);
}
