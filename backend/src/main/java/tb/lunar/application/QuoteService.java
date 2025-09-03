package tb.lunar.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tb.lunar.infrastructure.jpa.entity.HotelRate;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.HotelRateRepository;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.MoneyDTO;
import tb.lunar.web.dto.TripQuoteDTO;
import tb.lunar.web.dto.TripQuoteRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional(readOnly = true)
public class QuoteService {

    private final SpaceshipRepository shipRepo;
    private final HotelRateRepository rateRepo;

    private static final BigDecimal KG_PER_PAX = new BigDecimal("90");
    private static final BigDecimal SERVICE_FEE_PCT = new BigDecimal("0.08");
    private static final BigDecimal GOV_TAX_PCT = new BigDecimal("0.05");
    private static final String CURRENCY = "USD";

    public QuoteService(SpaceshipRepository shipRepo, HotelRateRepository rateRepo) {
        this.shipRepo = shipRepo;
        this.rateRepo = rateRepo;
    }

    public TripQuoteDTO quote(TripQuoteRequest req) {
        Spaceship s = shipRepo.findById(req.spaceshipId())
                .orElseThrow(() -> new IllegalArgumentException("Spaceship not found: " + req.spaceshipId()));
        var ft = s.getFuelType();
        if (ft == null) throw new IllegalStateException("Spaceship has no fuel type bound");

        HotelRate rate = rateRepo.findOne(req.destination(), req.place(), req.room())
                .orElseThrow(() -> new IllegalArgumentException("Hotel rate not found"));

        BigDecimal payloadKgBig = BigDecimal.valueOf(Math.max(req.payloadKg(), 0))
                .add(KG_PER_PAX.multiply(BigDecimal.valueOf(Math.max(req.passengers(), 1))));

        if (ft.getCarryCapacity() == null || ft.getCarryCapacity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Fuel carry_capacity must be > 0");
        }

        BigDecimal fuelUnitsBig = payloadKgBig
                .divide(ft.getCarryCapacity(), 0, RoundingMode.CEILING);
        long estFuelKg = fuelUnitsBig.longValue();

        BigDecimal fuelCost = BigDecimal.valueOf(estFuelKg)
                .multiply(ft.getPrice())
                .setScale(2, RoundingMode.HALF_UP);

        int rooms = req.rooms() > 0 ? req.rooms() : (req.passengers() + 1) / 2;
        BigDecimal hotelCost = rate.getPricePerDay()
                .multiply(BigDecimal.valueOf(Math.max(req.nights(), 1)))
                .multiply(BigDecimal.valueOf(Math.max(rooms, 1)))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal subtotal = fuelCost.add(hotelCost);
        BigDecimal serviceFee = subtotal.multiply(SERVICE_FEE_PCT).setScale(2, RoundingMode.HALF_UP);
        BigDecimal govTax = subtotal.multiply(GOV_TAX_PCT).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(serviceFee).add(govTax).setScale(2, RoundingMode.HALF_UP);

        return new TripQuoteDTO(
                s.getName(),
                s.getBooster(),
                ft.getName(),
                req.flightDate(),
                req.passengers(),
                req.payloadKg(),
                estFuelKg,
                req.destination(),
                req.place(),
                req.room(),
                req.nights(),
                rooms,
                new MoneyDTO(fuelCost, CURRENCY),
                new MoneyDTO(hotelCost, CURRENCY),
                new MoneyDTO(serviceFee, CURRENCY),
                new MoneyDTO(govTax, CURRENCY),
                new MoneyDTO(subtotal, CURRENCY),
                new MoneyDTO(total, CURRENCY)
        );
    }
}
