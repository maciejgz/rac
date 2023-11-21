package pl.mg.rac.simulation.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimulationUser {

    private String name;
    private boolean isRenting;
    private BigDecimal balance;

}
