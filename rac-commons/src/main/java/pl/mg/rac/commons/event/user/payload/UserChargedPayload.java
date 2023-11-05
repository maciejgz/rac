package pl.mg.rac.commons.event.user.payload;

import java.math.BigDecimal;

public record UserChargedPayload(String username, BigDecimal previousBalance, BigDecimal currentBalance) {
}
