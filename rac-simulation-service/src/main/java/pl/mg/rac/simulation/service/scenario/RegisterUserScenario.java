package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.model.SimulationLocation;
import pl.mg.rac.simulation.model.SimulationUser;
import pl.mg.rac.simulation.service.client.UserServiceClient;
import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

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
    public SimulationResult execute(int id) {
        log.debug("SCENARIO: RegisterUserScenario");
        try {
            SimulationUser user = new SimulationUser(
                    UUID.randomUUID().toString(),
                    SimulationLocation.getRandomLocationInWarsaw(),
                    BigDecimal.ZERO,
                    null,
                    false
            );
            return userServiceClient.registerUser(user);
        } catch (InterruptedException | IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
            return new SimulationResult("RegisterUserScenario", false, e.getMessage());
        }
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
