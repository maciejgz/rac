
package pl.mg.rac.location.application.dto.command;

import pl.mg.rac.commons.value.Location;

import javax.annotation.Nonnull;

public record UpdateCarLocationCommand(
        @Nonnull
        String vin,
        @Nonnull
        Location location) {
}
