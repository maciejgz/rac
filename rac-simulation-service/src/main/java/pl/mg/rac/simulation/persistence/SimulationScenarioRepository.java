package pl.mg.rac.simulation.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationScenarioRepository extends MongoRepository<SimulationScenarioEntity, Long> {

}
