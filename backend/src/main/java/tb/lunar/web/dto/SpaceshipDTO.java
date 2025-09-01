package tb.lunar.web.dto;

import tb.lunar.infrastructure.jpa.entity.Spaceship;

import java.util.Objects;

public class SpaceshipDTO {

    private Long id;
    private String name;
    private String booster;            // engine
    private double weight;
    private double maximumCapacity;
    private String fuelType;

    public SpaceshipDTO() {
    }

    public SpaceshipDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpaceshipDTO(Long id, String name, String booster, double maximumCapacity, String fuelType) {
        this(id, name, booster, 0.0d, maximumCapacity, fuelType);
    }

    public SpaceshipDTO(Long id, String name, String booster, double weight,
                        double maximumCapacity, String fuelType) {
        this.id = id;
        this.name = name;
        this.booster = booster;
        this.weight = weight;
        this.maximumCapacity = maximumCapacity;
        this.fuelType = fuelType;
    }

    public static SpaceshipDTO fromEntity(Spaceship s) {
        return new SpaceshipDTO(
                s.getId(),
                s.getName(),
                s.getBooster(),
                s.getWeight() != null ? s.getWeight().doubleValue() : 0.0d,
                s.getMaximumCapacity() != null ? s.getMaximumCapacity().doubleValue() : 0.0d,
                s.getFuelType() != null ? s.getFuelType().getName() : null
        );
    }

    public static SpaceshipDTO fromEntity2(Spaceship s) {
        return new SpaceshipDTO(
                s.getId(),
                s.getName(),
                s.getBooster(),
                s.getMaximumCapacity() != null ? s.getMaximumCapacity().doubleValue() : 0.0d,
                s.getFuelType() != null ? s.getFuelType().getName() : null
        );
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(double maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceshipDTO that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SpaceshipDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", booster='" + booster + '\'' +
                ", weight=" + weight +
                ", maximumCapacity=" + maximumCapacity +
                ", fuelType='" + fuelType + '\'' +
                '}';
    }
}
