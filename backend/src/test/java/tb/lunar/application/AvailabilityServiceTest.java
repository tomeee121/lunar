package tb.lunar.application;

import tb.lunar.domain.travel.Location;
import tb.lunar.domain.travel.TravelPlan;
import tb.lunar.domain.travel.TravelPlanRegistry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tb.lunar.infrastructure.jpa.entity.FuelType;
import tb.lunar.infrastructure.jpa.entity.HotelRate;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.infrastructure.jpa.repo.HotelRateRepository;
import tb.lunar.infrastructure.jpa.repo.SpaceshipRepository;
import tb.lunar.web.dto.AvailabilityDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AvailabilityServiceTest {
    SpaceshipRepository ships = mock(SpaceshipRepository.class);
    HotelRateRepository hotelRates = mock(HotelRateRepository.class);
    TravelPlanRegistry registry = mock(TravelPlanRegistry.class);
    AvailabilityService service;

    @BeforeEach
    void setUp() {
        service = new AvailabilityService(ships, hotelRates, registry);
    }

    @Test
    void shouldComputeCostsForArmstrong() {
        TravelPlan moon = mock(TravelPlan.class);
        when(registry.get("moon")).thenReturn(moon);
        when(moon.code()).thenReturn("moon");
        when(moon.staysForPackage("armstrong"))
                .thenReturn(Map.of(Location.CYCLER, 13, Location.SURFACE, 6));

        var cycler = rate("CYCLER", "standard", "300");
        var surface = rate("SURFACE", "armstrong", "300");
        when(hotelRates.findByDestinationAndPlace("moon", "CYCLER")).thenReturn(List.of(cycler));
        when(hotelRates.findByDestinationAndPlace("moon", "SURFACE")).thenReturn(List.of(surface));

        var fuel = new FuelType();
        fuel.setName("LOX/RP-1");
        fuel.setCarryCapacity(new BigDecimal("0.5"));
        fuel.setPrice(new BigDecimal("0.202"));

        var ship = new Spaceship();
        ship.setId(1L);
        ship.setName("Dragon");
        ship.setWeight(new BigDecimal("549045"));
        ship.setMaximumCapacity(new BigDecimal("22800"));
        ship.setFuelType(fuel);

        when(ships.findAllWithFuel()).thenReturn(List.of(ship));

        List<AvailabilityDTO> out = service.listAvailability(
                "moon", LocalDate.parse("2025-09-01"), 3, "armstrong", null);

        assertThat(out).hasSize(1);
        var dto = out.get(0);
        assertThat(dto.spaceshipName()).isEqualTo("Dragon");
        assertThat(dto.hotelCost()).isEqualByComparingTo(new BigDecimal("5700.00"));
        assertThat(dto.totalCost()).isGreaterThan(dto.hotelCost());
    }

    private static HotelRate rate(String place, String room, String price) {
        var r = new HotelRate();
        r.setPlace(place);
        r.setRoom(room);
        r.setPricePerDay(new BigDecimal(price));
        r.setDestination("moon");
        return r;
    }
}
