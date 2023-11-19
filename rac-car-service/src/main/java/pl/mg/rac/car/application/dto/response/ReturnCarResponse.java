package pl.mg.rac.car.application.dto.response;

import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record ReturnCarResponse(String vin, BigDecimal distanceTraveled, String rentalId, Location location,
                                boolean success) {
}
