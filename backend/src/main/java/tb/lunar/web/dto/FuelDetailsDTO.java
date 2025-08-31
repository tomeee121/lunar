package tb.lunar.web.dto;

import java.math.BigDecimal;

public record FuelDetailsDTO(
        Long spaceshipId,
        String spaceshipName,
        String fuelType,          //ie "LOX/RP-1"
        BigDecimal carriedWeight, // weigh (spaceship + passengers)
        BigDecimal fuelNeeded,    // weight of fuel
        BigDecimal pricePerUnit,  // price of fuel/kg
        BigDecimal fuelCost,      // costs (fuelNeeded * pricePerUnit)
        BigDecimal markup,        // as 3 in the notes
        BigDecimal flightCost     // final cost (fuelCost * markup)
) {}