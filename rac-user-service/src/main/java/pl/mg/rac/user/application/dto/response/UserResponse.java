package pl.mg.rac.user.application.dto.response;

import java.math.BigDecimal;

public record UserResponse(String name, BigDecimal balance) {
}
