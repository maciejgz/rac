package pl.mg.rac.location.domain.service;

import lombok.extern.slf4j.Slf4j;
import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.domain.model.CarLocation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
public class CarLocationDomainService {

    private static final int EARTH_RADIUS_KM = 6378137;

    public BigDecimal calculateDistanceTraveled(List<CarLocation> positions) {
        if (positions.size() < 2) {
            return BigDecimal.ZERO;
        }
        BigDecimal distance = BigDecimal.ZERO;
        for (int i = 1; i < positions.size(); i++) {
            distance = distance.add(calculateDistanceBetweenTwoLocations(positions.get(i - 1).getLocation(), positions.get(i).getLocation()));
        }
        return distance.setScale(2, RoundingMode.DOWN);
    }

    public static BigDecimal calculateDistanceBetweenTwoLocations(Location location1, Location location2) {
        double lat1 = location1.latitude().doubleValue();
        double lon1 = location1.longitude().doubleValue();
        double lat2 = location2.latitude().doubleValue();
        double lon2 = location2.longitude().doubleValue();


        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c;

        return BigDecimal.valueOf(distance / 1000).setScale(2, RoundingMode.HALF_DOWN);
    }

}
