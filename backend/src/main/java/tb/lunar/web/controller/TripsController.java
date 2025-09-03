package tb.lunar.web.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tb.lunar.infrastructure.jpa.entity.FuelType;
import tb.lunar.infrastructure.jpa.entity.HotelRate;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.HotelRateRepository;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.MoneyDTO;
import tb.lunar.web.dto.QuoteRequestDTO;
import tb.lunar.web.dto.TripQuoteDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripsController {

    private final SpaceshipRepository ships;
    private final HotelRateRepository rates;

    public TripsController(SpaceshipRepository ships, HotelRateRepository rates) {
        this.ships = ships;
        this.rates = rates;
    }

    @GetMapping("/rooms")
    public List<String> rooms(@RequestParam String destination, @RequestParam String place) {
        return rates.findByDestinationIgnoreCaseAndPlaceIgnoreCaseOrderByPricePerDayAsc(destination, place)
                .stream().map(r -> r.getRoom()).toList();
    }

    @PostMapping("/quote")
    public TripQuoteDTO quote(@Valid @RequestBody QuoteRequestDTO req) {
        Spaceship ship = ships.findById(req.spaceshipId())
                .orElseThrow(() -> new IllegalArgumentException("Spaceship not found"));

        FuelType fuel = ship.getFuelType();

        // kg masy do wyniesienia: payload + pasażerowie*90kg
        BigDecimal payloadKg = BigDecimal.valueOf(Math.max(0, req.payloadKg()));
        BigDecimal paxKg = BigDecimal.valueOf(req.passengers()).multiply(BigDecimal.valueOf(90));
        BigDecimal estWeightKg = payloadKg.add(paxKg);

        // liczba jednostek paliwa = ceil(kg / carryCapacity)
        BigDecimal fuelUnits = estWeightKg.divide(fuel.getCarryCapacity(), 0, RoundingMode.CEILING);

        long estFuelKg = fuelUnits.longValue(); // gdy baseUnit=kg to to samo; zachowujemy liczbowo do podglądu

        // koszt paliwa
        BigDecimal fuelCost = fuelUnits
                .multiply(fuel.getPrice())
                .setScale(2, RoundingMode.HALF_UP);

        // stawka hotelowa
        HotelRate rate = rates.findByDestinationIgnoreCaseAndPlaceIgnoreCaseAndRoomIgnoreCase(
                        req.destination(), req.place(), req.room())
                .orElseThrow(() -> new IllegalArgumentException("Hotel rate not found"));

        BigDecimal hotelCost = rate.getPricePerDay()
                .multiply(BigDecimal.valueOf(req.nights()))
                .multiply(BigDecimal.valueOf(req.rooms()))
                .setScale(2, RoundingMode.HALF_UP);

        // fee i podatek przykładowo
        BigDecimal subtotal = fuelCost.add(hotelCost);
        BigDecimal serviceFee = subtotal.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal govTax = subtotal.multiply(BigDecimal.valueOf(0.12)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(serviceFee).add(govTax).setScale(2, RoundingMode.HALF_UP);

        return new TripQuoteDTO(
                ship.getName(),
                ship.getBooster(),
                fuel.getName(),
                req.flightDate(),
                req.passengers(),
                req.payloadKg(),
                estFuelKg,
                req.destination(),
                req.place(),
                req.room(),
                req.nights(),
                req.rooms(),
                new MoneyDTO(fuelCost, "USD"),
                new MoneyDTO(hotelCost, "USD"),
                new MoneyDTO(serviceFee, "USD"),
                new MoneyDTO(govTax, "USD"),
                new MoneyDTO(subtotal, "USD"),
                new MoneyDTO(total, "USD")
        );
    }
}
