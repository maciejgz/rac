package pl.mg.rac.location.infrastructure.out.persistence;

import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.dto.exception.LocationNotFoundException;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.domain.model.CarLocation;
import pl.mg.rac.location.infrastructure.out.persistence.entity.CarLocationEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CarLocationRepository implements CarLocationDatabase {
    private final CarLocationJpaRepository carRepository;

    public CarLocationRepository(CarLocationJpaRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void saveCarLocation(CarLocation carLocation) {
        CarLocationEntity entity = new CarLocationEntity(
                new CarLocationEntity.CarKey(carLocation.getVin(),
                        Instant.now()
                ),
                BigDecimal.valueOf(carLocation.getLocation().latitude()),
                BigDecimal.valueOf(carLocation.getLocation().longitude())
        );
        CarLocationEntity savedEntity = carRepository.save(entity);
        ofEntity(savedEntity);
    }

    @Override
    public List<CarLocation> findLocationBetween(String vin, Instant from, Instant to) {
        List<CarLocationEntity> entities = carRepository.findByKeyVinAndKeyTimestampBetween(vin, from, to);
        return entities.stream()
                .map(CarLocationRepository::ofEntity)
                .toList();
    }

    @Override
    public CarLocation findLatestLocation(String vin) throws LocationNotFoundException {
        Optional<CarLocationEntity> loc = carRepository.findFirstByKeyVinOrderByKeyTimestampDesc(vin);
        if (loc.isPresent()) {
            return ofEntity(loc.get());
        } else {
            throw new LocationNotFoundException("Location for vin " + vin + " not found");
        }
    }

    private static CarLocation ofEntity(CarLocationEntity entity) {
        return new CarLocation(
                entity.getKey().getVin(),
                new Location(entity.getLatitude().doubleValue(), entity.getLongitude().doubleValue()),
                entity.getKey().getTimestamp()
        );
    }
}
