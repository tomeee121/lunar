package tb.lunar.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "hotel_rate")
public class HotelRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ie. "moon" */
    @Column(nullable = false, length = 50)
    private String destination;

    /** "CYCLER" | "SURFACE" */
    @Column(nullable = false, length = 20)
    private String place;

    /** np. "standard" | "panoramic" | "penthouse" | "armstrong" | "conrad" | "aldrin" */
    @Column(nullable = false, length = 50)
    private String room;

    @Column(name = "price_per_day", nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerDay;

    public HotelRate() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }
}
