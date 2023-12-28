package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.model.SimulationCar;
import pl.mg.rac.simulation.model.SimulationLocation;
import pl.mg.rac.simulation.persistence.SimulationScenarioEntity;
import pl.mg.rac.simulation.persistence.SimulationScenarioRepository;
import pl.mg.rac.simulation.service.client.CarServiceClient;
import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@Component
@Slf4j
@RefreshScope
public class AddCarScenario implements SimulationScenario {

    private final CarServiceClient carServiceClient;
    private final SimulationScenarioRepository repository;

    @Value("${rac.simulation.probability.add-car-scenario}")
    private double probability;

    public AddCarScenario(CarServiceClient carServiceClient, SimulationScenarioRepository repository) {
        this.carServiceClient = carServiceClient;
        this.repository = repository;
    }

    @Override
    public SimulationResult execute(int id) {
        log.debug("SCENARIO: AddCarScenario");
        try {
            SimulationCar car = new SimulationCar(UUID.randomUUID().toString(),
                    SimulationLocation.getRandomLocationInWarsaw(),
                    0L,
                    null,
                    false,
                    null
            );
            return carServiceClient.addCar(car);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new SimulationResult("AddCarScenario", false, e.getMessage());
        }
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
