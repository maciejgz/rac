package pl.mg.rac.simulation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mg.rac.simulation.model.scenario.SimulationScenario;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class SimulationService {

    private final List<SimulationScenario> scenarios;

    public SimulationService(List<SimulationScenario> scenarios) {
        this.scenarios = scenarios;
        this.scenarios.sort(
                Comparator.comparingDouble(SimulationScenario::getProbability)
        );
    }

    public void startSimulation() {
        executeScenario();
    }

    public void executeScenario() {
        double random = Math.random();
        for (SimulationScenario scenario : scenarios) {
            random -= scenario.getProbability();
            if (random <= 0) {
                scenario.execute();
                break;
            }
        }
    }

}
