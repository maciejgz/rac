package pl.mg.rac.rent.application.dto.command;

import javax.annotation.Nonnull;

public record RequestReturnCommand(
        @Nonnull
        String rentId) {
}
