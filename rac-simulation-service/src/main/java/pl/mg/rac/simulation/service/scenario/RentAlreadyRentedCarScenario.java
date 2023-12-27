package pl.mg.rac.simulation.service.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.mg.rac.simulation.service.scenario.model.SimulationResult;

@Component
@Slf4j
@RefreshScope
public class RentAlreadyRentedCarScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.rent-already-rented-car-scenario}")
    private double probability;

    @Override
    public SimulationResult execute(int id) {
        log.debug("execute() RentAlreadyRentedCarScenario");
        //TODO implement scenario
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
