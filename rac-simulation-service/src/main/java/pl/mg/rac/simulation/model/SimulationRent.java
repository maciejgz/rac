package pl.mg.rac.simulation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class SimulationRent {

    private String rentId;
    private String status;
    private String username;
    private String vin;
    private String statusReason;

}
