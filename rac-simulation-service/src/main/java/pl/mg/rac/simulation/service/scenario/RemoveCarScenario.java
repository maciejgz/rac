package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.service.client.CarServiceClient;

import java.io.IOException;
import java.net.URISyntaxException;

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
    public void execute(int id) {
        log.debug("execute() RemoveCarScenario");
        try {
            carServiceClient.deleteCar(carServiceClient.getRandomCar().orElseThrow().getVin());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
