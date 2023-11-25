package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.service.client.UserServiceClient;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Slf4j
@RefreshScope
public class RemoveUserScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.remove-user-scenario}")
    private double probability;

    private final UserServiceClient userServiceClient;

    public RemoveUserScenario(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public void execute() {
        log.debug("execute() RemoveUserScenario");
        try {
            userServiceClient.deleteUser(userServiceClient.getRandomUser().getName());
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
