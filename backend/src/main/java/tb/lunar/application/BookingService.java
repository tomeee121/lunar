package tb.lunar.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tb.lunar.infrastructure.jpa.entity.AppUser;
import tb.lunar.infrastructure.jpa.entity.Booking;
import tb.lunar.infrastructure.jpa.entity.Flight;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final FlightRepository flights;
    private final SpaceshipRepository ships;
    private final BookingRepository bookings;
    private final AppUserRepository users;

    private static final BigDecimal PASSENGER_WEIGHT_KG = new BigDecimal("110");

    public BookingService(FlightRepository flights, SpaceshipRepository ships,
                          BookingRepository bookings, AppUserRepository users) {
        this.flights = flights; this.ships = ships; this.bookings = bookings; this.users = users;
    }

    @Transactional
    public Booking create(Long userId, Long spaceshipId, LocalDate date,
                          int passengers, String passengerName, String pkgCode) {

        Spaceship ship = ships.findById(spaceshipId)
                .orElseThrow(() -> new IllegalArgumentException("Spaceship not found"));
        Flight flight = flights.findBySpaceshipIdAndFlightDate(spaceshipId, date)
                .orElseGet(() -> {
                    Flight f = new Flight();
                    f.setSpaceshipId(spaceshipId);
                    f.setFlightDate(date);
                    return flights.save(f);
                });

        BigDecimal required = PASSENGER_WEIGHT_KG.multiply(BigDecimal.valueOf(passengers));
        BigDecimal newBooked = flight.getBookedCapacity().add(required);

        if (newBooked.compareTo(ship.getMaximumCapacity()) > 0) {
            throw new IllegalArgumentException("Not enough capacity for selected date");
        }

        flight.setBookedCapacity(newBooked);

        AppUser user = users.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Booking b = new Booking();
        b.setFlight(flight);
        b.setPassengersCount(passengers);
        b.setPassengerName(passengerName != null ? passengerName : user.getEmail());
        b.setPackageCode(pkgCode);
        b.setUser(user);

        return bookings.save(b);
    }

    public List<Booking> listForUser(Long userId) {
        AppUser user = users.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return bookings.findByUserOrderByCreatedAtDesc(user);
    }
}
