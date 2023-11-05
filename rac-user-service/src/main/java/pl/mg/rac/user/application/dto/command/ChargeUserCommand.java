package pl.mg.rac.user.application.dto.command;

import java.math.BigDecimal;

public record ChargeUserCommand(String name, BigDecimal amount) {
}
