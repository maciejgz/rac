
package pl.mg.rac.location.application.dto.command;

import pl.mg.rac.commons.value.Location;

import javax.annotation.Nonnull;

public record UpdateUserLocationCommand(
        @Nonnull
        String username,
        @Nonnull
        Location location) {
}
