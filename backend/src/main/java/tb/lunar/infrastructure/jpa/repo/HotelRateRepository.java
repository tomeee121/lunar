package tb.lunar.infrastructure.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tb.lunar.infrastructure.jpa.entity.HotelRate;

import java.util.List;

public interface HotelRateRepository extends JpaRepository<HotelRate, Long> {

    List<HotelRate> findByDestination(String destination);

    List<HotelRate> findByDestinationAndPlace(String destination, String place);
}
