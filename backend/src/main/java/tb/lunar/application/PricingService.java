package tb.lunar.application;

import org.springframework.stereotype.Service;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.web.dto.FuelDetailsDTO;

import java.math.BigDecimal;

@Service
public class PricingService {

    public FuelDetailsDTO calculateFuelDetails(Spaceship ship, double cargoWeight) {
        var carriedWeight = ship.getWeight().add(BigDecimal.valueOf(cargoWeight));
        var carryCapacity = ship.getFuelType().getCarryCapacity();
        var fuelNeeded = carriedWeight.divide(carryCapacity, 2, BigDecimal.ROUND_HALF_UP);

        var pricePerUnit = ship.getFuelType().getPrice();
        var fuelCost = fuelNeeded.multiply(pricePerUnit);

        var markup = BigDecimal.valueOf(3.0); // markup
        var flightCost = fuelCost.multiply(markup);

        return new FuelDetailsDTO(
                ship.getId(),
                ship.getName(),
                ship.getFuelType().getName(),
                carriedWeight,
                fuelNeeded,
                pricePerUnit,
                fuelCost,
                markup,
                flightCost
        );
    }
}