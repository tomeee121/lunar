package tb.lunar.infrastructure.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tb.lunar.infrastructure.jpa.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
