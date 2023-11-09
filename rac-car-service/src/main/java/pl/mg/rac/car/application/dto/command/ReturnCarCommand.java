package pl.mg.rac.car.application.dto.command;

public record ReturnCarCommand(
        String vin,
        double distanceTraveled,
        String rentalId
) {
}
