package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.model.SimulationCar;
import pl.mg.rac.simulation.model.SimulationLocation;
import pl.mg.rac.simulation.service.client.CarServiceClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@Component
@Slf4j
@RefreshScope
public class AddCarScenario implements SimulationScenario {

    private final CarServiceClient carServiceClient;

    @Value("${rac.simulation.probability.add-car-scenario}")
    private double probability;

    public AddCarScenario(CarServiceClient carServiceClient) {
        this.carServiceClient = carServiceClient;
    }

    @Override
    public void execute() {
        log.debug("execute() AddCarScenario");
        try {
            SimulationCar car = new SimulationCar(UUID.randomUUID().toString(),
                    SimulationLocation.getRandomLocationInWarsaw(),
                    0L,
                    false,
                    null
            );
            carServiceClient.addCar(car);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
