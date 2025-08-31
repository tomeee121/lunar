package tb.lunar.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "spaceship")
public class Spaceship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String booster;

    /**
     * ship weight [NUMERIC]
     */
    @Column(nullable = false)
    private BigDecimal weight;

    /**
     * max nr of kg it can take to the space
     */
    @Column(name = "maximum_capacity", nullable = false)
    private BigDecimal maximumCapacity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fuel_type_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_spaceship_fuel_type"))
    private FuelType fuelType;

    public Spaceship() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBooster() {
        return booster;
    }

    public void setBooster(String booster) {
        this.booster = booster;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(BigDecimal maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}
