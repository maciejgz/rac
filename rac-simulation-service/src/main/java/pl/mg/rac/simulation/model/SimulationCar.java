package pl.mg.rac.simulation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationCar {

    private String vin;
    private SimulationLocation location;
    private Long mileage;
    private String rentalId;
    private boolean failure;
    private String failureReason;

}
