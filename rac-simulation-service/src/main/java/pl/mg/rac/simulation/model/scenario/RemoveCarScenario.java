package pl.mg.rac.simulation.model.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RemoveCarScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.remove-car-scenario}")
    private double probability;

    @Override
    public void execute() {
        log.debug("execute() RemoveCarScenario");
        //TODO implement
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
