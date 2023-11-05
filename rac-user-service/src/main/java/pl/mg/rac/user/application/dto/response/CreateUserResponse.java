package pl.mg.rac.user.application.dto.response;

import java.math.BigDecimal;

public record CreateUserResponse(String name, BigDecimal balance) {
}
