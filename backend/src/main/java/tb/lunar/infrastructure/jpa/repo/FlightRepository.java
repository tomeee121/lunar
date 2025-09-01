package tb.lunar.infrastructure.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tb.lunar.infrastructure.jpa.entity.Flight;

import java.time.LocalDate;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findBySpaceshipIdAndFlightDate(Long spaceshipId, LocalDate date);
}
