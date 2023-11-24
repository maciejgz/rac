package pl.mg.rac.simulation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimulationCar {

    private String vin;
    private SimulationLocation location;
    private Long mileage;
    private boolean isRented;
    private String rentalId;

}
