package pl.mg.rac.location.infrastructure.out.persistence;

import pl.mg.rac.commons.value.Location;
import pl.mg.rac.location.application.port.out.CarLocationDatabase;
import pl.mg.rac.location.domain.model.CarLocation;
import pl.mg.rac.location.infrastructure.out.persistence.entity.CarLocationEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class CarLocationRepository implements CarLocationDatabase {
    private final CarLocationJpaRepository carRepository;

    public CarLocationRepository(CarLocationJpaRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void saveCarLocation(CarLocation carLocation) {
        CarLocationEntity entity = new CarLocationEntity(
                carLocation.getId(),
                carLocation.getVin(),
                BigDecimal.valueOf(carLocation.getLocation().latitude()),
                BigDecimal.valueOf(carLocation.getLocation().longitude()),
                Instant.now()
        );
        CarLocationEntity savedEntity = carRepository.save(entity);
        ofEntity(savedEntity);
    }

    @Override
    public List<CarLocation> findLocationBetween(String vin, Instant from, Instant to) {
        List<CarLocationEntity> entities = carRepository.findByVinAndTimestampBetween(vin, from, to);
        return entities.stream()
                .map(CarLocationRepository::ofEntity)
                .toList();
    }

    @Override
    public CarLocation findLatestLocation(String vin) {
        return ofEntity(carRepository.findFirstByVinOrderByTimestampDesc(vin));
    }

    private static CarLocation ofEntity(CarLocationEntity entity) {
        return new CarLocation(
                entity.getId(),
                entity.getVin(),
                new Location(entity.getLatitude().doubleValue(), entity.getLongitude().doubleValue()),
                entity.getTimestamp()
        );
    }
}
