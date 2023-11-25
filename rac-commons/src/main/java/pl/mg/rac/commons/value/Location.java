package pl.mg.rac.commons.value;

import java.math.BigDecimal;

public record Location(
        BigDecimal latitude,
        BigDecimal longitude
) {
}
