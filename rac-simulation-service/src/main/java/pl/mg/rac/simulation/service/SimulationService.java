package pl.mg.rac.simulation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import pl.mg.rac.simulation.service.scenario.SimulationScenario;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RefreshScope
public class SimulationService {

    private final List<SimulationScenario> scenarios;

    public SimulationService(List<SimulationScenario> scenarios) {
        this.scenarios = scenarios;
        this.scenarios.sort(
                Comparator.comparingDouble(SimulationScenario::getProbability)
        );
    }

    public void startSimulation(long numberOfScenarios) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(100)) {
            for (int i = 0; i < numberOfScenarios; i++) {
                int number = i;
                executorService.submit(() -> executeScenario(number));
                log.debug("Scenario finished no: {}", i);
            }
        }
    }

    public void executeScenario(int id) {
        SecureRandom secureRandom = new SecureRandom();
        double random = secureRandom.nextDouble();
        for (SimulationScenario scenario : scenarios) {
            random -= scenario.getProbability();
            if (random <= 0) {
                scenario.execute(id);
                break;
            }
        }
    }

}
