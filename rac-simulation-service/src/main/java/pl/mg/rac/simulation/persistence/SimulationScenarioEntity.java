package pl.mg.rac.simulation.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "simulation_scenario_result")
@Data
public class SimulationScenarioEntity {

    @Id
    public String id;

    private final Long scenarioId;
    private final String scenarioType;
    private final Boolean success;
    private final String message;

}
