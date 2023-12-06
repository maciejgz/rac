package pl.mg.rac.user.domain.model.policy;

import java.math.BigDecimal;
import java.time.Instant;

public interface ChargeUserPolicy {

    BigDecimal calculateRentCharge(Instant rentStartDate, Instant rentEndDate, BigDecimal distanceTraveled);

}
