package pl.mg.rac.car.application.dto.command;

import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record ReturnCarCommand(
        String vin,
        BigDecimal distanceTraveled,
        String rentalId,
        Location location
) {
}
