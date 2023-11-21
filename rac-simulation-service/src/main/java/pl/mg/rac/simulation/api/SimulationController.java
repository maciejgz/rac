package pl.mg.rac.simulation.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.rac.simulation.service.SimulationService;

import java.net.URI;

@RestController
@RequestMapping("/simulation")
@Slf4j
public class SimulationController {

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PutMapping(value = "")
    public ResponseEntity<Void> startSimulation()  {
        simulationService.startSimulation();
        return ResponseEntity.ok().build();
    }


}
