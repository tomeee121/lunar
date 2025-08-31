package tb.lunar.web.dto;

import tb.lunar.infrastructure.jpa.entity.Spaceship;

public record SpaceshipDTO(
        Long id,
        String name,
        String booster, //engine
        double weight,
        double maximumCapacity,
        String fuelType
) {
    public static SpaceshipDTO fromEntity(Spaceship spaceship) {
        return new SpaceshipDTO(
                spaceship.getId(),
                spaceship.getName(),
                spaceship.getBooster(),
                spaceship.getWeight().doubleValue(),
                spaceship.getMaximumCapacity().doubleValue(),
                spaceship.getFuelType().getName()
        );
    }
}