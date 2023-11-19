package pl.mg.rac.car.application.dto.response;

import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record AddCarResponse(
        String vin,
        Location location,
        BigDecimal mileage
) {
}
