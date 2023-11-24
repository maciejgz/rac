package pl.mg.rac.simulation.model.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RefreshScope
public class RemoveUserScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.remove-user-scenario}")
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
