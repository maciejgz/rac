package pl.mg.rac.simulation.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;

public record SimulationLocation(BigDecimal latitude, BigDecimal longitude) {

    private static final int EARTH_RADIUS_KM = 6378137;


    /**
     * This method is not so accurate, but it's enough for our purposes.
     *
     * @param location location
     * @param radius   radius in kilometers
     * @return random location in radius from given location
     */
    public static SimulationLocation randomOfOtherLocation(SimulationLocation location, double radius) {
        double lat = location.latitude.doubleValue();
        double lon = location.longitude.doubleValue();

        // Convert radius from kilometers to meters
        double radiusInRad = radius / ((double) EARTH_RADIUS_KM / 1000);

        SecureRandom random = new SecureRandom();

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInRad * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of east-west distances
        double newLat = lat + y * 180 / Math.PI;
        double newLon = lon + x * 180 / Math.PI / Math.cos(lat * Math.PI / 180);

        BigDecimal newLatitude = BigDecimal.valueOf(newLat).setScale(6, RoundingMode.HALF_UP);
        BigDecimal newLongitude = BigDecimal.valueOf(newLon).setScale(6, RoundingMode.HALF_UP);

        return new SimulationLocation(newLatitude, newLongitude);
    }


    /**
     * This method is not so accurate, but it's enough for our purposes.
     *
     * @param location1 first location
     * @param location2 second location
     * @return distance between two locations in kilometers, 2 decimal places
     */
    public static BigDecimal calculateDistanceBetweenTwoLocations(SimulationLocation location1, SimulationLocation location2) {
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

    /**
     * @return random location in Poland
     */
    public static SimulationLocation getRandomLocationInWarsaw() {
        //return random location in Warsaw
        SecureRandom secureRandom = new SecureRandom();
        BigDecimal latitude = BigDecimal.valueOf(52.0 + secureRandom.nextDouble());
        BigDecimal longitude = BigDecimal.valueOf(20.0 + secureRandom.nextDouble() * 1.5d);
        return new SimulationLocation(latitude, longitude);
    }
}

