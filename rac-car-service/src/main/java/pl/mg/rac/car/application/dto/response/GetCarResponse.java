package pl.mg.rac.car.application.dto.response;

import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.value.Location;

public record GetCarResponse(String vin, Location location, boolean rented, double mileage, String rentalId) {

    public GetCarResponse ofCar(Car car) {
        return new GetCarResponse(car.getVin(), car.getLocation(), car.getRented(), car.getMileage(), car.getRentalId());
    }

}
