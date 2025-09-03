package tb.lunar.infrastructure.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tb.lunar.infrastructure.jpa.entity.HotelRate;

import java.util.List;
import java.util.Optional;

public interface HotelRateRepository extends JpaRepository<HotelRate, Long> {

    List<HotelRate> findByDestination(String destination);

    List<HotelRate> findByDestinationAndPlace(String destination, String place);

    @Query("""
     select r from HotelRate r
     where lower(r.destination) = lower(:dest)
       and lower(r.place) = lower(:place)
       and lower(r.room) = lower(:room)
     """)
    Optional<HotelRate> findOne(@Param("dest") String destination,
                                @Param("place") String place,
                                @Param("room") String room);

    List<HotelRate> findByDestinationIgnoreCaseAndPlaceIgnoreCaseOrderByPricePerDayAsc(String destination, String place);
    Optional<HotelRate> findByDestinationIgnoreCaseAndPlaceIgnoreCaseAndRoomIgnoreCase(String destination, String place, String room);

}
