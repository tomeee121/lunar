package tb.lunar.infrastructure.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fuel_type")
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ie "LOX/RP-1" */
    @Column(nullable = false, length = 80)
    private String name;

    /** ie "kg" */
    @Column(name = "base_unit", nullable = false, length = 10)
    private String baseUnit;

    /** kg of weight per 1 base unit of fuel ratio */
    @Column(name = "carry_capacity", nullable = false)
    private BigDecimal carryCapacity;

    /** price for base_unit [NUMERIC] */
    @Column(nullable = false)
    private BigDecimal price;

    public FuelType() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBaseUnit() { return baseUnit; }
    public void setBaseUnit(String baseUnit) { this.baseUnit = baseUnit; }
    public BigDecimal getCarryCapacity() { return carryCapacity; }
    public void setCarryCapacity(BigDecimal carryCapacity) { this.carryCapacity = carryCapacity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}

