package pl.mg.rac.commons.event.user.payload;

import pl.mg.rac.commons.event.RacEventPayload;

import java.math.BigDecimal;

public record UserChargedPayload(String username, BigDecimal previousBalance, BigDecimal currentBalance) implements RacEventPayload {
}
