package pl.mg.rac.rent.application.dto.command;

import pl.mg.rac.commons.value.Location;

import javax.annotation.Nonnull;

public record RequestRentCommand(
        @Nonnull
        String username,
        @Nonnull
        String vin,
        @Nonnull
        Location location) {
}
