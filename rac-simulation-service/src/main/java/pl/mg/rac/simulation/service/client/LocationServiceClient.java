package pl.mg.rac.simulation.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mg.rac.simulation.model.SimulationLocation;

@Service
@Slf4j
public class LocationServiceClient implements ServiceClient {

    void publishUserLocation(String username, SimulationLocation location) {
        log.info("Publishing user location: {} {}", username, location);
        //TODO implement
    }

    void publishCarLocation(String vin, SimulationLocation location) {
        log.info("Publishing user location: {} {}", vin, location);
        //TODO implement
    }

}
