package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.model.SimulationCar;
import pl.mg.rac.simulation.service.client.CarServiceClient;
import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
@Slf4j
@RefreshScope
public class RemoveCarScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.remove-car-scenario}")
    private double probability;

    private final CarServiceClient carServiceClient;

    public RemoveCarScenario(CarServiceClient carServiceClient) {
        this.carServiceClient = carServiceClient;
    }

    @Override
    public SimulationResult execute(int id) {
        log.debug("execute() RemoveCarScenario");
        try {
            Optional<SimulationCar> randomCar = carServiceClient.getRandomCar();
            if (randomCar.isEmpty()) {
                log.debug("execute() RemoveCarScenario randomCar is empty");
                return new SimulationResult("RemoveCarScenario", false, "No cars in database");
            }
            return carServiceClient.deleteCar(randomCar.get().getVin());
        } catch (InterruptedException | IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
            return new SimulationResult("RemoveCarScenario", false, e.getMessage());
        }
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
