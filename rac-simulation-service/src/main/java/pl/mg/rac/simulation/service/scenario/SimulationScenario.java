package pl.mg.rac.simulation.service.scenario;

public interface SimulationScenario {
    void execute(int id);

    double getProbability();

}
