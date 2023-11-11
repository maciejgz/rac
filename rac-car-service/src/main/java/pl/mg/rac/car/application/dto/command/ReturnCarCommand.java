package pl.mg.rac.car.application.dto.command;

import pl.mg.rac.commons.value.Location;

public record ReturnCarCommand(
        String vin,
        double distanceTraveled,
        String rentalId,
        Location location
) {
}
