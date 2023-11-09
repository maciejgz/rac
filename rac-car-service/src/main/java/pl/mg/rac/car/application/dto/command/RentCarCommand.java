package pl.mg.rac.car.application.dto.command;

public record RentCarCommand(
        String vin,
        String rentalId
) {
}
