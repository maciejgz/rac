package pl.mg.rac.simulation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationRent {

    private String rentId;
    private String status;
    private String username;
    private String vin;
    private String statusReason;

}
