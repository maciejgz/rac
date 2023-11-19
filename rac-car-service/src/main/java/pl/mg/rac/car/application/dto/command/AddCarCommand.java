package pl.mg.rac.car.application.dto.command;

import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record AddCarCommand(
        String vin,
        Location location,
        BigDecimal mileage
) {
}
