package pl.mg.rac.car.application.dto.command;

import pl.mg.rac.commons.value.Location;

public record AddCarCommand(
        String vin,
        Location location,
        double mileage
) {
}
