package pl.mg.rac.car.application.dto.response;

import pl.mg.rac.commons.value.Location;

public record AddCarResponse(
        String vin,
        Location location,
        double mileage
) {
}
