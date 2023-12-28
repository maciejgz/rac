package pl.mg.rac.simulation.service.scenario;

import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

public interface SimulationScenario {
    SimulationResult execute(int id);
    double getProbability();

}
