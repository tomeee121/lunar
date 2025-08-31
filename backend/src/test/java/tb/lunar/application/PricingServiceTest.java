package tb.lunar.application;


import tb.lunar.infrastructure.jpa.entity.FuelType;
import tb.lunar.infrastructure.jpa.entity.Spaceship;
import tb.lunar.web.dto.FuelDetailsDTO;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PricingServiceTest {

    private final PricingService pricing = new PricingService();

    @Test
    void shouldCalculateFuelDetails_fromShipFuelFields() {
        FuelType fuel = new FuelType();
        fuel.setName("LOX/RP-1");
        fuel.setCarryCapacity(new BigDecimal("0.5"));
        fuel.setPrice(new BigDecimal("0.202"));

        Spaceship ship = new Spaceship();
        ship.setId(1L);
        ship.setName("Dragon");
        ship.setWeight(new BigDecimal("549045"));
        ship.setMaximumCapacity(new BigDecimal("22800"));
        ship.setFuelType(fuel);

        double cargo = 20000.0;

        FuelDetailsDTO out = pricing.calculateFuelDetails(ship, cargo);

        BigDecimal carriedWeight = ship.getWeight().add(BigDecimal.valueOf(cargo));
        assertThat(out.carriedWeight()).isEqualByComparingTo(carriedWeight);

        BigDecimal fuelNeededExpected = carriedWeight
                .divide(fuel.getCarryCapacity(), 2, BigDecimal.ROUND_HALF_UP);
        assertThat(out.fuelNeeded()).isEqualByComparingTo(fuelNeededExpected);

        BigDecimal fuelCostExpected = fuelNeededExpected.multiply(fuel.getPrice());
        assertThat(out.fuelCost()).isEqualByComparingTo(fuelCostExpected);

        BigDecimal markup = new BigDecimal("3.0");
        BigDecimal flightCostExpected = fuelCostExpected.multiply(markup);
        assertThat(out.flightCost()).isEqualByComparingTo(flightCostExpected);
    }

    @Test
    void zeroCargo_stillUsesShipMass() {
        FuelType fuel = new FuelType();
        fuel.setName("LOX/LH2");
        fuel.setCarryCapacity(new BigDecimal("0.4"));
        fuel.setPrice(new BigDecimal("0.250"));

        Spaceship ship = new Spaceship();
        ship.setId(2L);
        ship.setName("New Moon");
        ship.setWeight(new BigDecimal("612487"));
        ship.setMaximumCapacity(new BigDecimal("25465"));
        ship.setFuelType(fuel);

        FuelDetailsDTO out = pricing.calculateFuelDetails(ship, 0.0);

        assertThat(out.carriedWeight()).isEqualByComparingTo(new BigDecimal("612487"));
        assertThat(out.fuelNeeded()).isEqualByComparingTo(new BigDecimal("1531217.50"));
        assertThat(out.fuelCost()).isGreaterThan(BigDecimal.ZERO);
        assertThat(out.flightCost()).isGreaterThan(out.fuelCost());
    }
}

