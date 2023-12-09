package pl.mg.rac.simulation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationUser {

    private String name;
    private SimulationLocation location;
    private BigDecimal balance;
    private String currentRentId;
    private boolean blocked;

}
