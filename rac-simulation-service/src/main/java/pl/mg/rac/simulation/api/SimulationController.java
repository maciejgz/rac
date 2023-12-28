package pl.mg.rac.simulation.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.rac.simulation.service.SimulationService;
import pl.mg.rac.simulation.service.SimulationServiceImpl;

@RestController
@RequestMapping("/simulation")
@Slf4j
public class SimulationController {

    private final SimulationService simulationService;

    public SimulationController(SimulationServiceImpl simulationService) {
        this.simulationService = simulationService;
    }

    @PutMapping(value = "/{numberOfScenarios}")
    public ResponseEntity<Void> startSimulation(@PathVariable long numberOfScenarios) {
        simulationService.startSimulation(numberOfScenarios);
        return ResponseEntity.ok().build();
    }


}
