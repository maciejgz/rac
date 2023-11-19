package pl.mg.rac.location.domain.service;

import pl.mg.rac.location.domain.model.CarLocation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.List;

public class CarLocationDomainService {

    public BigDecimal calculateDistanceTraveled(List<CarLocation> positions) {
        //TODO implement calculation of distance traveled based on the positions list
        //generate random vale in BigDecimal between 1 and 100
        SecureRandom random = new SecureRandom();
        return BigDecimal.valueOf(random.nextDouble() * 100).setScale(2, RoundingMode.DOWN);
    }

}
