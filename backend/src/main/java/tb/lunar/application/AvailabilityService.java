package tb.lunar.application;
import org.springframework.stereotype.Service;
import tb.lunar.domain.travel.Location;
import tb.lunar.domain.travel.TravelPlan;
import tb.lunar.domain.travel.TravelPlanRegistry;
import tb.lunar.infrastructure.jpa.entity.HotelRate;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.HotelRateRepository;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.AvailabilityDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 * - validate max capacity
 * - count flight cost (fuel × price × markup)
 * - count hotel costs using TravelPlan
 */
@Service
public class AvailabilityService {

    private final SpaceshipRepository ships;
    private final HotelRateRepository hotelRates;
    private final TravelPlanRegistry travelPlans;

    private static final BigDecimal PASSENGER_WEIGHT_KG = new BigDecimal("110"); // average person weight
    private static final BigDecimal DEFAULT_MARKUP = new BigDecimal("3.0");

    public AvailabilityService(SpaceshipRepository ships,
                               HotelRateRepository hotelRates,
                               TravelPlanRegistry travelPlans) {
        this.ships = ships;
        this.hotelRates = hotelRates;
        this.travelPlans = travelPlans;
    }

    public List<AvailabilityDTO> listAvailability(String destination,
                                                  LocalDate date,
                                                  int passengers,
                                                  String packageCode,
                                                  String roomsSelection) {
        TravelPlan plan = travelPlans.get(destination);

        Map<Location, String> selection = parseRoomsSelection(roomsSelection);

        var allShips = ships.findAllWithFuel();

        return allShips.stream()
                .map(s -> priceForShip(plan, s, passengers, packageCode, selection))
                .toList();
    }

    private AvailabilityDTO priceForShip(TravelPlan plan,
                                         Spaceship ship,
                                         int passengers,
                                         String packageCode,
                                         Map<Location, String> selection) {

        validateCapacity(ship, passengers);

        // fuel × price × markup
        BigDecimal carriedPayload = PASSENGER_WEIGHT_KG.multiply(BigDecimal.valueOf(passengers));
        BigDecimal carriedWeightTotal = ship.getWeight().add(carriedPayload);                     // ship + luggage

        BigDecimal carryCapacityPerKg = ship.getFuelType().getCarryCapacity();                   // kg luggage / 1 kg fuel
        BigDecimal fuelNeeded = carriedWeightTotal.divide(carryCapacityPerKg, 6, RoundingMode.HALF_UP);

        BigDecimal pricePerUnit = ship.getFuelType().getPrice();
        BigDecimal fuelCost = fuelNeeded.multiply(pricePerUnit);

        BigDecimal flightCost = fuelCost.multiply(DEFAULT_MARKUP);

        //  hotels - price × nights
        BigDecimal hotelCost = hotelCostFor(plan, packageCode, selection);

        BigDecimal total = flightCost.add(hotelCost);
        BigDecimal perPassenger = passengers > 0
                ? total.divide(BigDecimal.valueOf(passengers), 2, RoundingMode.HALF_UP)
                : total;

        return new AvailabilityDTO(
                ship.getId(),
                ship.getName(),
                packageCode,
                passengers,
                round2(flightCost),
                round2(hotelCost),
                round2(total),
                round2(perPassenger)
        );
    }

    private void validateCapacity(Spaceship ship, int passengers) {
        BigDecimal required = PASSENGER_WEIGHT_KG.multiply(BigDecimal.valueOf(passengers));
        if (required.compareTo(ship.getMaximumCapacity()) > 0) {
            throw new CapacityExceededException(
                    "Required " + required + " kg exceeds max " + ship.getMaximumCapacity() +
                            " kg for " + ship.getName()
            );
        }
    }

    private BigDecimal hotelCostFor(TravelPlan plan, String pkg, Map<Location, String> selection) {
        Map<Location, Integer> stays = plan.staysForPackage(pkg);

        BigDecimal total = BigDecimal.ZERO;
        for (var entry : stays.entrySet()) {
            Location loc = entry.getKey();
            int nights = entry.getValue();

            String place = loc.name(); // "CYCLER" / "SURFACE"
            List<HotelRate> rates = hotelRates.findByDestinationAndPlace(plan.code(), place);

            BigDecimal dayPrice;
            String chosenRoom = selection.get(loc); // np. "panoramic"
            if (chosenRoom != null && !chosenRoom.isBlank()) {
                dayPrice = rates.stream()
                        .filter(r -> r.getRoom().equalsIgnoreCase(chosenRoom))
                        .map(HotelRate::getPricePerDay)
                        .min(Comparator.naturalOrder())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Room '" + chosenRoom + "' not available for place " + place));
            } else {
                // fallback: najtańszy pokój
                dayPrice = rates.stream()
                        .map(HotelRate::getPricePerDay)
                        .min(Comparator.naturalOrder())
                        .orElse(BigDecimal.ZERO);
            }

            total = total.add(dayPrice.multiply(BigDecimal.valueOf(nights)));
        }
        return total;
    }

    /**
    * "CYCLER:panoramic,SURFACE:armstrong" ->
     * {
     *   Location.CYCLER = "panoramic",
     *   Location.SURFACE = "armstrong"
     * }
    * */
    private Map<Location, String> parseRoomsSelection(String rooms) {
        if (rooms == null || rooms.isBlank()) return Map.of();
        Map<Location, String> map = new HashMap<>();
        for (String pair : rooms.split(",")) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                Location place = Location.valueOf(kv[0].trim().toUpperCase()); // CYCLER/SURFACE
                map.put(place, kv[1].trim());
            }
        }
        return map;
    }

    private static BigDecimal round2(BigDecimal v) {
        return v.setScale(2, RoundingMode.HALF_UP);
    }

    public static class CapacityExceededException extends RuntimeException {
        public CapacityExceededException(String message) { super(message); }
    }
}