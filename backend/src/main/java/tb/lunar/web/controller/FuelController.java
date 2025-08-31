package tb.lunar.web.controller;

import org.springframework.web.bind.annotation.*;
import tb.lunar.application.PricingService;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.FuelDetailsDTO;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    private final SpaceshipRepository spaceshipRepository;
    private final PricingService pricingService;

    public FuelController(SpaceshipRepository spaceshipRepository,
                          PricingService pricingService) {
        this.spaceshipRepository = spaceshipRepository;
        this.pricingService = pricingService;
    }

    @GetMapping("/{id}")
    public FuelDetailsDTO fuelDetails(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") double cargoWeight) {

        Spaceship ship = spaceshipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No spaceship with id " + id));

        return pricingService.calculateFuelDetails(ship, cargoWeight);
    }
}