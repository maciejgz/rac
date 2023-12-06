package pl.mg.rac.user.domain.model.policy;

import java.math.BigDecimal;
import java.time.Instant;

public class OldUserChargePolicy implements ChargeUserPolicy {

    @Override
    public BigDecimal calculateRentCharge(Instant rentStartDate, Instant rentEndDate, BigDecimal distanceTraveled) {
        return BigDecimal.valueOf((double) (rentEndDate.getEpochSecond() - rentStartDate.getEpochSecond()) / 60 * 0.2)
                .add(distanceTraveled.multiply(BigDecimal.valueOf(0.1)));
    }
}
