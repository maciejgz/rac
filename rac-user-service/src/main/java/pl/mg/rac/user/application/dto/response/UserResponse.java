package pl.mg.rac.user.application.dto.response;

import pl.mg.rac.commons.value.Location;

import java.math.BigDecimal;

public record UserResponse(String name, BigDecimal balance, Location location) {
}
