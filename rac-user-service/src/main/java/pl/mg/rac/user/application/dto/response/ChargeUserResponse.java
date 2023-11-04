package pl.mg.rac.user.application.dto.response;

import java.math.BigDecimal;

public record ChargeUserResponse(String name, BigDecimal balance) {
}
