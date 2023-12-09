package pl.mg.rac.car.application.dto.response;

import pl.mg.rac.car.domain.model.Car;
import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record GetCarResponse(String vin, Location location, BigDecimal mileage, String rentalId, Boolean failure,
                             String failureReason) {

    public GetCarResponse ofCar(Car car) {
        return new GetCarResponse(car.getVin(), car.getLocation(), car.getMileage(), car.getRentalId(), car.getFailure(), car.getFailureReason());
    }

}
