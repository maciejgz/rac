package pl.mg.rac.simulation.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SimulationLocationTest {

    @Test
    void calculateDistanceBetweenTwoLocationsTest() {
        SimulationLocation location1 = new SimulationLocation(new BigDecimal("52.2296756"), new BigDecimal("21.0122287"));
        SimulationLocation location2 = new SimulationLocation(new BigDecimal("52.406374"), new BigDecimal("16.9251681"));

        BigDecimal distance = SimulationLocation.calculateDistanceBetweenTwoLocations(location1, location2);

        assertEquals(new BigDecimal("278.77"), distance);
    }

    @Test
    void randomOfOtherLocationTest() {
        SimulationLocation location = new SimulationLocation(new BigDecimal("52.2296756"), new BigDecimal("21.0122287"));

        SimulationLocation randomLocation = SimulationLocation.randomOfOtherLocation(location, 1);
        assertNotNull(randomLocation);
        for (int i = 0; i < 100; i++) {
            randomLocation = SimulationLocation.randomOfOtherLocation(location, 1);
            assertNotNull(randomLocation);
            assertTrue(SimulationLocation.calculateDistanceBetweenTwoLocations(location, randomLocation).compareTo(new BigDecimal("1")) <= 0);
        }
    }

    @Test
    void getRandomLocationInWarsawTest() {
        SimulationLocation randomLocation = SimulationLocation.getRandomLocationInWarsaw();
        assertNotNull(randomLocation);
        //check if random location is in Warsaw
        assertTrue(randomLocation.latitude().compareTo(new BigDecimal("52.0")) >= 0);
        assertTrue(randomLocation.latitude().compareTo(new BigDecimal("53.0")) <= 0);
        assertTrue(randomLocation.longitude().compareTo(new BigDecimal("20.0")) >= 0);
        assertTrue(randomLocation.longitude().compareTo(new BigDecimal("22.0")) <= 0);
    }
}