package pl.mg.rac.simulation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mg.rac.simulation.persistence.SimulationScenarioEntity;
import pl.mg.rac.simulation.persistence.SimulationScenarioRepository;
import pl.mg.rac.simulation.service.scenario.SimulationScenario;
import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class SimulationServiceImpl implements SimulationService {

    private final List<SimulationScenario> scenarios;
    private final SimulationScenarioRepository repository;

    public SimulationServiceImpl(List<SimulationScenario> scenarios, SimulationScenarioRepository repository) {
        this.scenarios = scenarios;
        this.repository = repository;
        this.scenarios.sort(
                Comparator.comparingDouble(SimulationScenario::getProbability)
        );
    }

    @Override
    @Transactional
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
                SimulationResult result = scenario.execute(id);
                repository.save(new SimulationScenarioEntity((long) id, result.scenarioName(), result.success(), result.result()));
                break;
            }
        }
    }

}
