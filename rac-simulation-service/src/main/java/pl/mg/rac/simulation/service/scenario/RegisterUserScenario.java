package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.model.SimulationLocation;
import pl.mg.rac.simulation.model.SimulationUser;
import pl.mg.rac.simulation.service.client.UserServiceClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.UUID;

@Component
@Slf4j
@RefreshScope
public class RegisterUserScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.add-user-scenario}")
    private double probability;

    private final UserServiceClient userServiceClient;

    public RegisterUserScenario(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public void execute() {
        log.debug("SCENARIO: RegisterUserScenario");
        try {
            SimulationUser user = new SimulationUser(
                    UUID.randomUUID().toString(),
                    SimulationLocation.getRandomLocationInWarsaw(),
                    false,
                    BigDecimal.ZERO,
                    null
            );
            userServiceClient.registerUser(user);
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
