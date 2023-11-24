package pl.mg.rac.simulation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SimulationUser {

    private String name;
    private SimulationLocation location;
    private boolean isRenting;
    private BigDecimal balance;
    private String currentRentId;

}
