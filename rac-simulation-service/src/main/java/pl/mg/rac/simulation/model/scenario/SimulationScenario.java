package pl.mg.rac.simulation.model.scenario;

public interface SimulationScenario {
    void execute();

    double getProbability();
}
