package pl.mg.rac.simulation.model.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddCarScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.add-car-scenario}")
    private double probability;

    @Override
    public void execute() {
        log.debug("execute() AddCarScenario");
        //TODO implement
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
