package tb.lunar.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_date", nullable = false)
    private LocalDate flightDate;

    /** carrying capacity already taken */
    @Column(name = "booked_capacity", nullable = false)
    private BigDecimal bookedCapacity = BigDecimal.ZERO;

    @Column(name = "spaceship_id", nullable = false)
    private Long spaceshipId;

    public Flight() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFlightDate() { return flightDate; }
    public void setFlightDate(LocalDate flightDate) { this.flightDate = flightDate; }
    public BigDecimal getBookedCapacity() { return bookedCapacity; }
    public void setBookedCapacity(BigDecimal bookedCapacity) { this.bookedCapacity = bookedCapacity; }

    public Long getSpaceshipId() {
        return spaceshipId;
    }

    public void setSpaceshipId(Long spaceshipId) {
        this.spaceshipId = spaceshipId;
    }
}
