package tb.lunar.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "hotel_rate")
public class HotelRate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destination;
    private String place;
    private String room;
    @Column(name = "price_per_day")
    private BigDecimal pricePerDay;

    // get/set
    public Long getId() { return id; }
    public String getDestination() { return destination; }
    public String getPlace() { return place; }
    public String getRoom() { return room; }
    public BigDecimal getPricePerDay() { return pricePerDay; }
}
