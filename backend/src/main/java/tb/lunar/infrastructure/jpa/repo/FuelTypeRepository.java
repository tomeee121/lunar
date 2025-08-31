package tb.lunar.infrastructure.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tb.lunar.infrastructure.jpa.entity.FuelType;

import java.util.Optional;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {
    Optional<FuelType> findByName(String s);
}
