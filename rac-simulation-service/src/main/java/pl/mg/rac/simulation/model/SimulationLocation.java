package pl.mg.rac.simulation.model;


import java.math.BigDecimal;
import java.math.RoundingMode;

public record SimulationLocation(BigDecimal latitude, BigDecimal longitude) {

    private static final int EARTH_RADIUS = 6378137;

    public static SimulationLocation randomOfOtherLocation(BigDecimal latitude, BigDecimal longitude, double radius) {
        double lat = latitude.doubleValue();
        double lon = longitude.doubleValue();

        // Convert radius from kilometers to meters
        radius *= 1000;

        // Offsets in meters
        double u = Math.random();
        double v = Math.random();
        double w = radius * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of east-west distances
        double newLat = x / EARTH_RADIUS;
        double newLon = y / (EARTH_RADIUS * Math.cos(Math.PI * newLat / 180));

        newLat = newLat + lat;
        newLon = newLon + lon;

        BigDecimal newLatitude = new BigDecimal(newLat).setScale(6, RoundingMode.HALF_UP);
        BigDecimal newLongitude = new BigDecimal(newLon).setScale(6, RoundingMode.HALF_UP);

        return new SimulationLocation(newLatitude, newLongitude);
    }

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
        double distance = EARTH_RADIUS * c;

        return BigDecimal.valueOf(distance);
    }

}

