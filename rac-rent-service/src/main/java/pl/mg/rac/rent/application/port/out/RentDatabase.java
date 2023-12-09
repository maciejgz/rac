package pl.mg.rac.rent.application.port.out;

import pl.mg.rac.rent.domain.model.Rent;

import java.util.List;
import java.util.Optional;

public interface RentDatabase {
    Rent save(Rent rent);

    Optional<Rent> findById(String rentId);

    List<Rent> findOpenRentRequests();

    List<Rent> findOpenReturnRequests();

    Optional<Rent> findOpenRentRequestByUserAndVin(String username, String vin);

    Optional<Rent> findOpenRentRequestByUser(String username);

    Optional<Rent> findOpenRentRequestByVin(String vin);
}
