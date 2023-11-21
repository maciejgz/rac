package pl.mg.rac.simulation.model.scenario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RentAlreadyRentedCarScenario implements SimulationScenario {

    @Value("${rac.simulation.probability.rent-already-rented-car-scenario}")
    private double probability;

    @Override
    public void execute() {
        log.debug("execute() RentAlreadyRentedCarScenario");
        //TODO implement
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
