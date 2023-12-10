package pl.mg.rac.simulation.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mg.rac.simulation.service.scenario.SimulationScenario;

@Repository
public interface SimulationScenarioRepository extends JpaRepository<SimulationScenario, Long> {
}
