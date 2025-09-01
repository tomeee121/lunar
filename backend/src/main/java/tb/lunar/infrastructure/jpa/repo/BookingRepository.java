package tb.lunar.infrastructure.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tb.lunar.infrastructure.jpa.entity.Booking;
import tb.lunar.infrastructure.jpa.entity.AppUser;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserOrderByCreatedAtDesc(AppUser user);
}
