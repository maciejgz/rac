package pl.mg.rac.simulation.model;

import lombok.Data;

@Data
public class SimulationRent {

    private String UUID;
    private SimulationRentStatus status;
    private SimulationUser user;

}
